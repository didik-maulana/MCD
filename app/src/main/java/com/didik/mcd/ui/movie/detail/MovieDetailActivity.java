package com.didik.mcd.ui.movie.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.entity.MovieDetail;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    private TextView tvTitle, tvRate, tvDate, tvOverview, tvRuntime, tvGenres, tvProductions;
    private ImageView imgBanner, imgPoster;
    private Toolbar toolbarMovie;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private Integer movieId;
    private MovieDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setIntentData();
        bindView();
        setupActionBar();
        setupViewModel();
        viewModel.fetchMovieDetail(movieId);
    }

    private void setIntentData() {
        movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
    }

    private void bindView() {
        tvTitle = findViewById(R.id.tv_title);
        tvRate = findViewById(R.id.tv_rate);
        tvDate = findViewById(R.id.tv_date);
        tvOverview = findViewById(R.id.tv_overview);
        tvRuntime = findViewById(R.id.tv_runtime);
        tvGenres = findViewById(R.id.tv_genres);
        tvProductions = findViewById(R.id.tv_productions);
        imgBanner = findViewById(R.id.img_banner);
        imgPoster = findViewById(R.id.img_poster);
        progressBar = findViewById(R.id.progress_bar);
        scrollView = findViewById(R.id.scroll_view);
        toolbarMovie = findViewById(R.id.movie_toolbar);
    }

    private void setupActionBar() {
        setSupportActionBar(toolbarMovie);
        toolbarMovie.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        viewModel.getMovieDetail().observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(MovieDetail movieDetail) {
                showMovieDetail(movieDetail);
            }
        });

        viewModel.getStateLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean state) {
                if (state) {
                    scrollView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    scrollView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                showErrorMessage(message);
            }
        });
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showMovieDetail(MovieDetail movieDetail) {
        tvTitle.setText(movieDetail.getTitle());
        tvRate.setText(movieDetail.getVoteAverage());
        tvDate.setText(movieDetail.getReleaseDate());
        tvOverview.setText(movieDetail.getOverview());
        tvRuntime.setText(movieDetail.getRuntime());
        tvGenres.setText(movieDetail.getGenres());
        tvProductions.setText(movieDetail.getProductionCompanies());

        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + movieDetail.getBackdropPath())
                .centerCrop()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(imgBanner);

        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + movieDetail.getPosterPath())
                .centerCrop()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(imgPoster);
    }
}
