package barayuda.com.movieapp.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import barayuda.com.movieapp.Constant;
import barayuda.com.movieapp.R;
import barayuda.com.movieapp.features.detail.DetailActivity;
import barayuda.com.movieapp.model.apiresponse.MovieItem;
import barayuda.com.movieapp.util.ImageURLBuilder;

/**
 * Created by barayuda on 7/28/2017..
 */

public class MostPopularViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_movie)ImageView poster;

    public MostPopularViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final MovieItem data){
        String src = ImageURLBuilder.getPosterURL(data.getPosterPath());
        Picasso.with(itemView.getContext())
                .load(src)
                .into(poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(itemView.getContext(), DetailActivity.class);
                detail.putExtra(Constant.KEY_MOVIE, new Gson().toJson(data));
                itemView.getContext().startActivity(detail);
            }
        });
    }
}
