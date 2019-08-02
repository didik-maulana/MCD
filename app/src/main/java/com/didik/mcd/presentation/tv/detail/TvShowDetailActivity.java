package com.didik.mcd.presentation.tv.detail;

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
import com.didik.mcd.model.TvShowDetail;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TV_SHOW_ID = "extra_tv_show_id";
    private TextView tvTitle, tvRate, tvDate, tvOverview, tvRuntime,
            tvGenres, tvProductions, tvEpisodeLabel, tvEpisode;
    private ImageView imgBanner, imgPoster;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private Integer tvShowId;
    private Observer<TvShowDetail> tvShowDetailObserver = new Observer<TvShowDetail>() {
        @Override
        public void onChanged(TvShowDetail tvShowDetail) {
            setUpTvShow(tvShowDetail);
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

        tvShowId = getIntent().getIntExtra(EXTRA_TV_SHOW_ID, 0);

        bindView();
        showEpisodeText();
        setUpViewModel();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_button_back) {
            onBackPressed();
        }
    }

    private void bindView() {
        tvTitle = findViewById(R.id.tv_title);
        tvRate = findViewById(R.id.tv_rate);
        tvDate = findViewById(R.id.tv_date);
        tvOverview = findViewById(R.id.tv_overview);
        tvGenres = findViewById(R.id.tv_genres);
        tvRuntime = findViewById(R.id.tv_runtime);
        tvProductions = findViewById(R.id.tv_productions);
        tvEpisode = findViewById(R.id.tv_episode);
        tvEpisodeLabel = findViewById(R.id.tv_episode_label);
        imgBanner = findViewById(R.id.img_banner);
        imgPoster = findViewById(R.id.img_poster);
        progressBar = findViewById(R.id.progress_bar);
        scrollView = findViewById(R.id.scroll_view);
        ImageButton imgButtonBack = findViewById(R.id.img_button_back);

        imgButtonBack.setOnClickListener(this);
    }

    private void showEpisodeText() {
        tvEpisode.setVisibility(View.VISIBLE);
        tvEpisodeLabel.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setUpViewModel() {
        TvShowDetailViewModel viewModel = ViewModelProviders.of(this).
                get(TvShowDetailViewModel.class);

        viewModel.getTvShow().observe(this, tvShowDetailObserver);
        viewModel.getStateLoading().observe(this, loadingObserver);
        viewModel.getErrorMessage().observe(this, errorMessageObserver);

        viewModel.fetchTvShowDetail(tvShowId);
    }

    private void setUpTvShow(TvShowDetail tvShow) {
        String releasedDate = new DateFormatter().formatDate(tvShow.getFirstAirDate());
        String runtime = new MovieDetailHelper().getRuntime(String.valueOf(tvShow.getEpisodeRuntime().get(0)));
        String genres = new MovieDetailHelper().getGenres(tvShow.getGenres());
        String productions = new MovieDetailHelper().getProductions(tvShow.getProductionCompanies());
        String episode = new MovieDetailHelper().getEpisode(tvShow.getNumberOfEpisode());

        tvTitle.setText(tvShow.getName());
        tvRate.setText(String.valueOf(tvShow.getVoteAverage()));
        tvDate.setText(releasedDate);
        tvOverview.setText(tvShow.getOverview());
        tvRuntime.setText(runtime);
        tvGenres.setText(genres);
        tvProductions.setText(productions);
        tvEpisode.setText(episode);

        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + tvShow.getBackdropPath())
                .placeholder(R.drawable.ic_image_placeholder)
                .centerCrop()
                .into(imgBanner);

        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + tvShow.getPosterPath())
                .placeholder(R.drawable.ic_image_placeholder)
                .centerCrop()
                .into(imgPoster);
    }
}
