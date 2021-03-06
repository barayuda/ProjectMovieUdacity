package barayuda.com.movieapp.features.detail;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import barayuda.com.movieapp.App;
import barayuda.com.movieapp.Constant;
import barayuda.com.movieapp.basemvp.BasePresenter;
import barayuda.com.movieapp.database.FavoriteContract;
import barayuda.com.movieapp.model.apiresponse.MovieItem;
import barayuda.com.movieapp.model.apiresponse.ReviewResponse;
import barayuda.com.movieapp.model.apiresponse.TrailerItem;
import barayuda.com.movieapp.model.apiresponse.TrailerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by barayuda on 7/28/2017.
 */

public class DetailPresenter implements BasePresenter<DetailView> {

    DetailView view;
    private Gson gson = new Gson();
    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks;
    private Call<TrailerResponse> trailerResponseCall;
    private Call<ReviewResponse> reviewResponseCall;

    @Override
    public void onAttach(DetailView BaseView) {
        view = BaseView;
    }

    @Override
    public void onDetach() {
        if(trailerResponseCall != null) trailerResponseCall.cancel();
        if(reviewResponseCall != null) reviewResponseCall.cancel();
        view = null;
    }

    void getData(String json){
        view.onDataReceived(gson.fromJson(json, MovieItem.class));
    }

    MovieItem getMovie(String json){
        return gson.fromJson(json, MovieItem.class);
    }

    void saveAsFavorite(ContentResolver resolver, MovieItem item){
        ContentValues cv = new ContentValues();
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIE_ID, item.getId());
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, item.getOriginalTitle());
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_BACKDROP, item.getBackdropPath());
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER, item.getPosterPath());
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_RATING, item.getVoteAverage());
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE, item.getReleaseDate());
        cv.put(FavoriteContract.FavoriteEntry.COLUMN_SYNOPSIS, item.getOverview());
        resolver.insert(FavoriteContract.FavoriteEntry.CONTENT_URI, cv);
    }

    void unsetAsFavorite(ContentResolver resolver, MovieItem item){
        long result = resolver.delete(
                uriWithIDBuilder(item.getId()),
                null,
                null
        );

        if(result > 0) view.onRefreshLoader();
    }

    void setupLoader(final Context context, final ContentResolver contentResolver, final long movieID){
        loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<Cursor>(context) {
                    @Override
                    public Cursor loadInBackground() {
                        try {
                            return contentResolver.query(
                                    uriWithIDBuilder(movieID),
                                    null,
                                    null,
                                    null,
                                    null
                            );
                        }catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onStartLoading() {
                        forceLoad();
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                setFavoriteButton(data.getCount());
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        };
    }

    void initLoader(LoaderManager loaderManager){
        loaderManager.initLoader(Constant.LOADER_ID, null, loaderCallbacks);
    }

    void restartLoader(LoaderManager loaderManager){
        loaderManager.restartLoader(Constant.LOADER_ID, null, loaderCallbacks);
    }

    private void setFavoriteButton(int count){
        if(count > 0){
            view.onStatusReceived(true);
        }else{
            view.onStatusReceived(false);
        }
    }

    private Uri uriWithIDBuilder(long id){
        return ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, id);
    }

    void getTrailers(long movieID){
        trailerResponseCall = App.getRestClient()
                .getService()
                .getTrailers(movieID);

        trailerResponseCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if(response.isSuccessful()){
                    List<TrailerItem> trailers = new ArrayList<TrailerItem>();
                    for(TrailerItem trailer : response.body().getResults()){
                        if(trailer.getType().equals(Constant.VIDEO_TRAILER)) trailers.add(trailer);
                    }
                    view.onTrailerDataReceived(trailers);
                }else{
                    view.onFailedReceiveData();
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                view.onFailedReceiveData();
                t.printStackTrace();
            }
        });
    }

    void getReviews(long movieID){
        reviewResponseCall = App.getRestClient()
                .getService()
                .getReviews(movieID);

        reviewResponseCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()){
                    view.onReviewDataReceived(response.body().getResults());
                }else{
                    view.onFailedReceiveData();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                view.onFailedReceiveData();
                t.printStackTrace();
            }
        });
    }
}
