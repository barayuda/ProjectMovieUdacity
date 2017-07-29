package barayuda.com.movieapp.features.detail;

import java.util.List;

import barayuda.com.movieapp.basemvp.BaseView;
import barayuda.com.movieapp.model.apiresponse.MovieItem;
import barayuda.com.movieapp.model.apiresponse.ReviewItem;
import barayuda.com.movieapp.model.apiresponse.TrailerItem;

/**
 * Created by barayuda on 7/28/2017.
 */

public interface DetailView extends BaseView {
    void onDataReceived(MovieItem movie);
    void onFailedReceiveData();
    void onStatusReceived(boolean isFavorite);
    void onRefreshLoader();
    void onTrailerDataReceived(List<TrailerItem> data);
    void onReviewDataReceived(List<ReviewItem> data);
}
