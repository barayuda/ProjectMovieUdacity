package barayuda.com.movieapp.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by barayuda on 7/28/2017.
 */

public class SnackBarBuilder {

    public static void showMessage(View view, String msg){
        Snackbar snackbarWarning = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbarWarning.show();
    }
}
