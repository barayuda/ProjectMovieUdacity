package barayuda.com.movieapp.util.rest;

import barayuda.com.movieapp.model.apiresponse.MovieResponse;
import barayuda.com.movieapp.model.apiresponse.ReviewResponse;
import barayuda.com.movieapp.model.apiresponse.TrailerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by barayuda on 7/28/2017..
 */

public interface APIService {

    @GET("movie/popular?")
    Call<MovieResponse> getPopularMovie(@Query("page") int page);

    @GET("movie/top_rated?")
    Call<MovieResponse> getHighRatedMovie(@Query("page") int page);

    @GET("movie/{id}/videos?")
    Call<TrailerResponse> getTrailers(@Path("id") long id);

    @GET("movie/{id}/reviews?")
    Call<ReviewResponse> getReviews(@Path("id") long id);
}
