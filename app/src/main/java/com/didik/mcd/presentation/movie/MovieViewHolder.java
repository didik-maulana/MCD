package com.didik.mcd.presentation.movie;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.didik.mcd.R;
import com.didik.mcd.model.Movie;

class MovieViewHolder {

    private TextView tvMovieTitle;
    private TextView tvMovieDate;
    private TextView tvMovieRate;
    private TextView tvMovieGenres;
    private TextView tvMovieRuntime;
    private ImageView imgMoviePoster;

    MovieViewHolder(View view) {
        tvMovieTitle = view.findViewById(R.id.tv_movie_title);
        tvMovieDate = view.findViewById(R.id.tv_movie_date);
        tvMovieRate = view.findViewById(R.id.tv_movie_rate);
        tvMovieGenres = view.findViewById(R.id.tv_movie_genres);
        tvMovieRuntime = view.findViewById(R.id.tv_movie_runtime);
        imgMoviePoster = view.findViewById(R.id.img_movie_poster);
    }

    void bindMovie(Movie movie) {
        tvMovieTitle.setText(movie.getTitle());
        tvMovieDate.setText(movie.getDate());
        tvMovieRate.setText(movie.getRate());
        tvMovieGenres.setText(movie.getGenres());
        tvMovieRuntime.setText(movie.getRuntime());
        imgMoviePoster.setImageResource(movie.getPoster());
    }
}
