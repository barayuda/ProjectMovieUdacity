package barayuda.com.movieapp.Entity;

import com.orm.SugarRecord;

/**
 * Created by barayuda on 5/20/2017.
 */
public class Favorite extends SugarRecord {
    private int movieId;
    private String note;

    public Favorite() {
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
