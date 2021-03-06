package barayuda.com.movieapp.features.main;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import barayuda.com.movieapp.Constant;
import barayuda.com.movieapp.R;
import barayuda.com.movieapp.adapter.MovieListAdapter;
import barayuda.com.movieapp.model.apiresponse.MovieItem;

/**
 * Created by barayuda on 7/28/2017.
 */

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.swipe_refresh)SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv)RecyclerView rv;
    @BindView(R.id.parent_main)RelativeLayout parentView;
    @BindString(R.string.highest_rated)String highRatedString;
    @BindString(R.string.most_popular)String mostPopularString;
    @BindString(R.string.favorites)String favoritesString;

    private MainPresenter presenter;
    private MovieListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private Parcelable layoutManagerSavedState;

    private String selectedSort;
    private ArrayList<MovieItem> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        if (savedInstanceState != null) {
            selectedSort = savedInstanceState.getString(Constant.KEY_SELECTED_CATEGORY);
            if(selectedSort.equals(Constant.CATEGORY_MOST_POPULAR)){
                getSupportActionBar().setSubtitle(mostPopularString);
            }else if(selectedSort.equals(Constant.CATEGORY_HIGH_RATED)){
                getSupportActionBar().setSubtitle(highRatedString);
            }else {
                getSupportActionBar().setSubtitle(favoritesString);
            }
            layoutManagerSavedState = savedInstanceState.getParcelable(Constant.LAYOUT_MANAGER);
            movies = savedInstanceState.getParcelableArrayList(Constant.KEY_STATE_MOVIE);
        }else{
            selectedSort = Constant.CATEGORY_MOST_POPULAR; //default
            getSupportActionBar().setSubtitle(mostPopularString);
        }

        onAttachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_high_rated:
                selectedSort = Constant.CATEGORY_HIGH_RATED;
                presenter.resetPage();
                presenter.loadData(selectedSort);
                getSupportActionBar().setSubtitle(highRatedString);
                return true;
            case R.id.menu_most_popular:
                selectedSort = Constant.CATEGORY_MOST_POPULAR;
                presenter.resetPage();
                presenter.loadData(selectedSort);
                getSupportActionBar().setSubtitle(mostPopularString);
                return true;
            case R.id.menu_favorites:
                selectedSort = Constant.CATEGORY_FAVORITES;
                getSupportActionBar().setSubtitle(favoritesString);

                presenter.setCategory(selectedSort);
                presenter.setupLoader(this, getContentResolver());
                presenter.restartLoader(getSupportLoaderManager());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.KEY_SELECTED_CATEGORY, selectedSort);
        outState.putParcelable(Constant.LAYOUT_MANAGER, rv.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(Constant.KEY_STATE_MOVIE, movies);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }

    @Override
    public void onAttachView() {
        presenter = new MainPresenter();
        presenter.onAttach(this);

        setupRecyclerView();
        if(selectedSort.equals(Constant.CATEGORY_FAVORITES)){
            presenter.setCategory(selectedSort);
            presenter.setupLoader(this, getContentResolver());
            presenter.restartLoader(getSupportLoaderManager());
        }else{
            presenter.loadData(selectedSort);
        }
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    void setupRecyclerView(){
        adapter = new MovieListAdapter();
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onDataReceived(List<MovieItem> data, int page) {
        Log.d("received", ""+data.size());
        if(swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);

        if(page>1){
            adapter.updateData(data);
        }else{
            movies.clear();
            for(MovieItem m : data){
                movies.add(m);
            }
            Log.d("movies size", ""+movies.size());
            adapter.replaceAll(data);
            Log.d("adaptersize", ""+adapter.getItemCount());

            //retain scroll position
            if(layoutManagerSavedState!=null){
                rv.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
            }
        }
    }

    @Override
    public void onLoadingData() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void onFailedReceivedData() {
        if(swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
        if(movies != null){
            adapter.replaceAll(movies);

            if(layoutManagerSavedState!=null){
                rv.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
            }
        }
        /*SnackBarBuilder.showMessage(
                parentView,
                getResources().getString(R.string.failed_load_data)
        );*/
    }

    @Override
    public void onRefresh() {
        if(selectedSort.equals(Constant.CATEGORY_FAVORITES)){
            presenter.restartLoader(getSupportLoaderManager());
        }else{
            presenter.resetPage();
            presenter.loadData(selectedSort);
        }
    }
}
