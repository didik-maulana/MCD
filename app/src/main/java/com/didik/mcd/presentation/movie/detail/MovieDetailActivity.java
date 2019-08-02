package com.didik.mcd.presentation.movie.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.helper.DateFormatter;
import com.didik.mcd.helper.MovieDetailHelper;
import com.didik.mcd.model.MovieDetail;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private TextView tvTitle, tvRate, tvDate, tvOverview, tvRuntime, tvGenres, tvProductions;
    private ImageView imgBanner, imgPoster;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private Integer movieId;
    private Observer<MovieDetail> movieDetailObserver = new Observer<MovieDetail>() {
        @Override
        public void onChanged(MovieDetail movieDetail) {
            showMovieDetail(movieDetail);
        }
    };

    private Observer<Boolean> loadingObserver = new Observer<Boolean>() {
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
    };

    private Observer<String> errorMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String message) {
            showErrorMessage(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);

        bindView();
        setUpViewModel();
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
        ImageButton imgButtonBack = findViewById(R.id.img_button_back);

        imgButtonBack.setOnClickListener(this);
    }

    private void setUpViewModel() {
        MovieDetailViewModel viewModel = ViewModelProviders.of(this)
                .get(MovieDetailViewModel.class);

        viewModel.getMovieDetail().observe(this, movieDetailObserver);
        viewModel.getStateLoading().observe(this, loadingObserver);
        viewModel.getErrorMessage().observe(this, errorMessageObserver);

        viewModel.fetchMovieDetail(movieId);
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showMovieDetail(MovieDetail movieDetail) {
        String releasedDate = new DateFormatter().formatDate(movieDetail.getReleaseDate());
        String genres = new MovieDetailHelper().getGenres(movieDetail.getGenres());
        String runtime = new MovieDetailHelper().getRuntime(movieDetail.getRuntime());
        String productions = new MovieDetailHelper().getProductions(movieDetail.getProductionCompanies());

        tvTitle.setText(movieDetail.getTitle());
        tvRate.setText(movieDetail.getVoteAverage());
        tvDate.setText(releasedDate);
        tvOverview.setText(movieDetail.getOverview());
        tvRuntime.setText(runtime);
        tvGenres.setText(genres);
        tvProductions.setText(productions);

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_button_back) {
            onBackPressed();
        }
    }
}
