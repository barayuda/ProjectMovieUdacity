package barayuda.com.movieapp.adapter.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import barayuda.com.movieapp.R;
import barayuda.com.movieapp.model.apiresponse.TrailerItem;
import barayuda.com.movieapp.util.TrailerUtil;

/**
 * Created by barayuda on 7/28/2017.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_trailer)ImageView img;
    @BindView(R.id.tv_title_trailer)TextView title;
    @BindView(R.id.tv_type_trailer)TextView type;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final TrailerItem data){
        Picasso.with(itemView.getContext())
                .load(TrailerUtil.getVideoThumbnailURL(data.getKey()))
                .into(img);

        title.setText(data.getName());
        type.setText(data.getType());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext()
                        .startActivity(new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(TrailerUtil.getYoutubeURL(data.getKey()))
                        ));
            }
        });
    }
}
