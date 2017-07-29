package barayuda.com.movieapp.basemvp;

/**
 * Created by barayuda on 7/28/2017..
 */

public interface BasePresenter<T extends BaseView> {
    void onAttach(T BaseView);
    void onDetach();
}
