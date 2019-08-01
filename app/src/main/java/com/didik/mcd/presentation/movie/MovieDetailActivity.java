package com.didik.mcd.presentation.movie;

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
        setContentView(R.layout.activity_detail);
        Objects.requireNonNull(getSupportActionBar()).hide();

        bindView();
        showDataMovie();
    }

    private void bindView() {
        tvTitle = findViewById(R.id.tv_title);
        tvRate = findViewById(R.id.tv_rate);
        tvDate = findViewById(R.id.tv_date);
        tvOverview = findViewById(R.id.tv_overview);
        tvRuntime = findViewById(R.id.tv_runtime);
        tvGenres = findViewById(R.id.tv_genres);
        tvDirector = findViewById(R.id.tv_director);
        tvActors = findViewById(R.id.tv_actors);
        imgBanner = findViewById(R.id.img_banner);
        imgPoster = findViewById(R.id.img_poster);
    }

    private void showDataMovie() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        tvTitle.setText(movie.getTitle());
//        tvRate.setText(movie.getRate());
//        tvDate.setText(movie.getDate());
//        tvOverview.setText(movie.getOverview());
//        tvRuntime.setText(movie.getRuntime());
//        tvGenres.setText(movie.getGenres());
//        tvDirector.setText(movie.getDirector());
//        tvActors.setText(movie.getActors());

//        Glide.with(this)
//                .load(movie.getPoster())
//                .centerCrop()
//                .into(imgBanner);
//
//        Glide.with(this)
//                .load(movie.getPoster())
//                .centerCrop()
//                .into(imgPoster);
    }
}
