package barayuda.com.movieapp.features.main;

import java.util.List;

import barayuda.com.movieapp.basemvp.BaseView;
import barayuda.com.movieapp.model.apiresponse.MovieItem;

/**
 * Created by barayuda on 7/28/2017.
 */

public interface MainView extends BaseView {
    void onDataReceived(List<MovieItem> data, int page);
    void onLoadingData();
    void onFailedReceivedData();
}
