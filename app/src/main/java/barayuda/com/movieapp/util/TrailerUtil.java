package barayuda.com.movieapp.util;

/**
 * Created by barayuda on 7/28/2017.
 */

public class TrailerUtil {
    public static String getVideoThumbnailURL(String key){
        return "http://img.youtube.com/vi/" + key + "/0.jpg";
    }

    public static String getYoutubeURL(String key){
        return "http://www.youtube.com/watch?v=" + key;
    }
}
