package com.didik.mcd.presentation.movie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.model.Movie;

class MovieViewHolder extends RecyclerView.ViewHolder {

    private ImageView imgMoviePoster;
    private TextView tvMovieTitle;
    private TextView tvMovieRate;
    private TextView tvMovieGenres;
    private TextView tvMovieRuntime;

    MovieViewHolder(View view, final MovieListener mItemListener) {
        super(view);

        tvMovieTitle = view.findViewById(R.id.tv_title);
        tvMovieRate = view.findViewById(R.id.tv_rate);
        tvMovieGenres = view.findViewById(R.id.tv_genres);
        tvMovieRuntime = view.findViewById(R.id.tv_runtime);
        imgMoviePoster = view.findViewById(R.id.img_poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onMovieClicked(getAdapterPosition());
            }
        });
    }

    void bindItem(Movie movie) {
        tvMovieTitle.setText(movie.getTitle());
        tvMovieRate.setText(String.valueOf(movie.getVoteAverage()));
//        tvMovieGenres.setText(movie.getGenres());
//        tvMovieRuntime.setText(movie.getRuntime());

        String imageUrl = BuildConfig.IMAGE_URL + movie.getPosterPath();
        Glide.with(itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(imgMoviePoster);
    }
}
