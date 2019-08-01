package com.didik.mcd.presentation.tv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.model.TvShow;

class TvShowViewHolder extends RecyclerView.ViewHolder {

    private ImageView imgPoster;
    private TextView tvTitle;
    private TextView tvRate;

    TvShowViewHolder(@NonNull View view, final TvShowListener mItemListener) {
        super(view);
        tvTitle = view.findViewById(R.id.tv_title);
        tvRate = view.findViewById(R.id.tv_rate);
        imgPoster = view.findViewById(R.id.img_poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onTvShowClicked(getAdapterPosition());
            }
        });
    }

    void bindItem(TvShow tvShow) {
        tvTitle.setText(tvShow.getName());
        tvRate.setText(String.valueOf(tvShow.getVoteAverage()));

        String imageUrl = BuildConfig.IMAGE_URL + tvShow.getPosterPath();
        Glide.with(itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(imgPoster);
    }
}
