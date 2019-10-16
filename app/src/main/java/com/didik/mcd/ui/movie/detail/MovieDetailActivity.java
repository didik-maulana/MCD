package com.didik.mcd.ui.movie.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
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
import com.didik.mcd.data.database.DatabaseClient;
import com.didik.mcd.data.entity.Favorite;
import com.didik.mcd.data.entity.MovieDetail;

import java.lang.ref.WeakReference;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    private TextView tvTitle, tvRate, tvDate, tvOverview, tvRuntime, tvGenres, tvProductions;
    private ImageView imgBanner, imgPoster, imgFavorite;
    private Toolbar toolbarMovie;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    private Integer movieId;
    private Boolean isFavorite = false;
    private MovieDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setIntentData();
        bindView();
        setupActionBar();
        setupViewModel();
        setupFavorite();
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
                showMessage(message);
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showMovieDetail(final MovieDetail movieDetail) {
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

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionClickFavorite(movieDetail);
            }
        });
    }

    private void setupFavorite() {
        GetFavoriteTask getFavoriteTask = new GetFavoriteTask(this);
        getFavoriteTask.execute();
    }

    private void actionClickFavorite(MovieDetail movie) {
        final Favorite favorite = new Favorite();
        favorite.setId(movieId);
        favorite.setTitle(movie.getTitle());
        favorite.setVoteAverage(movie.getVoteAverage());
        favorite.setPosterPath(movie.getPosterPath());
        favorite.setFavorite(true);
        favorite.setType(getString(R.string.key_movies));

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
        private WeakReference<MovieDetailActivity> activity;

        private GetFavoriteTask(MovieDetailActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DatabaseClient.getInstance(activity.get())
                    .getAppDatabase()
                    .getFavoriteDao()
                    .getIsFavorite(activity.get().movieId);
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
        private WeakReference<Favorite> movieReference;
        private WeakReference<MovieDetailActivity> activity;

        private InsertFavoriteTask(MovieDetailActivity activity, Favorite movieReference) {
            this.movieReference = new WeakReference<>(movieReference);
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseClient
                    .getInstance(activity.get())
                    .getAppDatabase()
                    .getFavoriteDao()
                    .addFavorite(movieReference.get());
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
        private WeakReference<MovieDetailActivity> activity;

        private RemoveFavoriteTask(MovieDetailActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseClient
                    .getInstance(activity.get())
                    .getAppDatabase()
                    .getFavoriteDao()
                    .removeFavorite(activity.get().movieId);
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
