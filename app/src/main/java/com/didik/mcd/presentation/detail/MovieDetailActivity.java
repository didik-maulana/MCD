package com.didik.mcd.presentation.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.didik.mcd.R;
import com.didik.mcd.model.Movie;

import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView tvTitle, tvRate, tvDate, tvOverview, tvRuntime, tvGenres, tvDirector, tvActors;
    private ImageView imgBanner, imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Objects.requireNonNull(getSupportActionBar()).hide();

        tvTitle = findViewById(R.id.tv_movie_title);
        tvRate = findViewById(R.id.tv_movie_rate);
        tvDate = findViewById(R.id.tv_movie_date);
        tvOverview = findViewById(R.id.tv_movie_overview);
        tvRuntime = findViewById(R.id.tv_movie_runtime);
        tvGenres = findViewById(R.id.tv_movie_genres);
        tvDirector = findViewById(R.id.tv_movie_director);
        tvActors = findViewById(R.id.tv_movie_actor);
        imgBanner = findViewById(R.id.img_movie_banner);
        imgPoster = findViewById(R.id.img_movie_poster);

        showDataMovie();
    }

    private void showDataMovie() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        tvTitle.setText(movie.getTitle());
        tvRate.setText(movie.getRate());
        tvDate.setText(movie.getDate());
        tvOverview.setText(movie.getOverview());
        tvRuntime.setText(movie.getRuntime());
        tvGenres.setText(movie.getGenres());
        tvDirector.setText(movie.getDirector());
        tvActors.setText(movie.getActors());
        imgBanner.setImageResource(movie.getPoster());
        imgPoster.setImageResource(movie.getPoster());
    }
}
