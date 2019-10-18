package com.didik.mcd.ui.tv.detail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.database.DatabaseClient;
import com.didik.mcd.data.entity.Favorite;
import com.didik.mcd.data.entity.TvShowDetail;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TV_SHOW_ID = "extra_tv_show_id";

    private TextView tvTitle, tvRate, tvDate, tvOverview, tvRuntime,
            tvGenres, tvProductions, tvEpisodeLabel, tvEpisode;
    private ImageView imgBanner, imgPoster, imgFavorite;
    private Toolbar toolbarMovie;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    private Integer tvShowId;
    private Boolean isFavorite = false;
    private TvShowDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setIntentData();
        bindView();
        setupActionBar();
        showEpisodeText();
        setupViewModel();
        setupFavorite();
        viewModel.fetchTvShowDetail(tvShowId);
    }

    private void setIntentData() {
        tvShowId = getIntent().getIntExtra(EXTRA_TV_SHOW_ID, 0);
    }

    @Override
    public void onClick(View view) {
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
        toolbarMovie = findViewById(R.id.movie_toolbar);
        imgFavorite = findViewById(R.id.img_favorite);
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

    private void showEpisodeText() {
        tvEpisode.setVisibility(View.VISIBLE);
        tvEpisodeLabel.setVisibility(View.VISIBLE);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);

        viewModel.getTvShow().observe(this, new Observer<TvShowDetail>() {
            @Override
            public void onChanged(TvShowDetail tvShowDetail) {
                setUpTvShow(tvShowDetail);
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
                showMessage(message);
            }
        });
    }

    private void setUpTvShow(final TvShowDetail tvShow) {

        tvTitle.setText(tvShow.getName());
        tvRate.setText(tvShow.getVoteAverage());
        tvDate.setText(tvShow.getFirstAirDate());
        tvOverview.setText(tvShow.getOverview());
        tvRuntime.setText(tvShow.getEpisodeRuntime());
        tvGenres.setText(tvShow.getGenres());
        tvProductions.setText(tvShow.getProductionCompanies());
        tvEpisode.setText(tvShow.getEpisodeRuntime());

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

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionClickFavorite(tvShow);
            }
        });
    }

    private void setupFavorite() {
        GetFavoriteTask getFavoriteTask = new GetFavoriteTask(this);
        getFavoriteTask.execute();
    }

    private void actionClickFavorite(TvShowDetail tvShow) {
        final Favorite favorite = new Favorite();
        favorite.setId(tvShowId);
        favorite.setTitle(tvShow.getName());
        favorite.setVoteAverage(tvShow.getVoteAverage());
        favorite.setPosterPath(tvShow.getPosterPath());
        favorite.setFavorite(true);
        favorite.setType(getString(R.string.key_tv_shows));

        InsertFavoriteTask insertFavoriteTask = new InsertFavoriteTask(this, favorite);
        RemoveFavoriteTask removeFavoriteTask = new RemoveFavoriteTask(this);

        if (isFavorite) {
            removeFavoriteTask.execute();
        } else {
            insertFavoriteTask.execute();
        }
        handleFavoriteIcon(!isFavorite);
    }

    private void handleFavoriteIcon(Boolean isFavorite) {
        if (isFavorite) {
            imgFavorite.setImageResource(R.drawable.ic_white_favorite);
        } else {
            imgFavorite.setImageResource(R.drawable.ic_favorite);
        }
    }

    private static class GetFavoriteTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<TvShowDetailActivity> activity;

        private GetFavoriteTask(TvShowDetailActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DatabaseClient.getInstance(activity.get())
                    .getAppDatabase()
                    .getFavoriteDao()
                    .getIsFavorite(activity.get().tvShowId);
        }

        @Override
        protected void onPostExecute(Boolean isFavorite) {
            super.onPostExecute(isFavorite);
            if (isFavorite == null) {
                activity.get().isFavorite = false;
                activity.get().handleFavoriteIcon(false);
            } else {
                activity.get().isFavorite = isFavorite;
                activity.get().handleFavoriteIcon(isFavorite);
            }
        }
    }

    static class InsertFavoriteTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Favorite> favoriteReference;
        private WeakReference<TvShowDetailActivity> activity;

        private InsertFavoriteTask(TvShowDetailActivity activity, Favorite favoriteReference) {
            this.favoriteReference = new WeakReference<>(favoriteReference);
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseClient
                    .getInstance(activity.get())
                    .getAppDatabase()
                    .getFavoriteDao()
                    .addFavorite(favoriteReference.get());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.get().isFavorite = true;
            activity.get().handleFavoriteIcon(true);
            activity.get().showMessage("Add to favorite");
        }
    }

    static class RemoveFavoriteTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<TvShowDetailActivity> activity;

        private RemoveFavoriteTask(TvShowDetailActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseClient
                    .getInstance(activity.get())
                    .getAppDatabase()
                    .getFavoriteDao()
                    .removeFavorite(activity.get().tvShowId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.get().isFavorite = false;
            activity.get().handleFavoriteIcon(false);
            activity.get().showMessage("Remove from favorite");
        }
    }
}
