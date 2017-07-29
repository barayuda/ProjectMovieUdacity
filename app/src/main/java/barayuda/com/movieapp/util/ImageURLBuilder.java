package barayuda.com.movieapp.util;

import barayuda.com.movieapp.BuildConfig;

/**
 * Created by barayuda on 7/28/2017.
 */

public class ImageURLBuilder {

    public static String getPosterURL(String path){
        return BuildConfig.BASE_URL_IMG + "w185" + path;
    }

    public static String getBackdropURL(String path){
        return BuildConfig.BASE_URL_IMG + "w300" + path;
    }
}
