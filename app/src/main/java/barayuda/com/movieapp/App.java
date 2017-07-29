package barayuda.com.movieapp;

import android.app.Application;

import barayuda.com.movieapp.util.rest.RestClient;

/**
 * Created by barayuda on 7/28/2017.
 */

public class App extends Application {

    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();

        restClient = new RestClient();
    }

    public static RestClient getRestClient(){
        return restClient;
    }
}
