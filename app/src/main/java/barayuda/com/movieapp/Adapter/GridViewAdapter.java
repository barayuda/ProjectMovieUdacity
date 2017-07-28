package barayuda.com.movieapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import barayuda.com.movieapp.Movie;
import barayuda.com.movieapp.R;

/**
 * Created by barayuda on 7/20/2017.
 */
public class GridViewAdapter extends BaseAdapter {
    private final Context context;
    private  List<Movie> urls = new ArrayList<Movie>();


    public GridViewAdapter(Context context, List<Movie> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Movie getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_poster, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.pb_imageView);
        progressBar.setVisibility(View.VISIBLE);

        // Get the image URL for the current position.
        Movie movie = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(movie.getPoster_path()) //
                //.placeholder(R.drawable.ic_hourglass_empty_black_36dp) //
                .centerCrop()
                .error(R.drawable.ic_error_black_36dp) //
                .fit() //
                .tag(context) //
                .into(imageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        return convertView ;
    }
}
