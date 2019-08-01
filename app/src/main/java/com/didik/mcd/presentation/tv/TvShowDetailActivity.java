package com.didik.mcd.presentation.tv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.didik.mcd.R;
import com.didik.mcd.model.TvShow;

import java.util.Objects;

public class TvShowDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    private TextView tvTitle, tvRate, tvDate, tvOverview, tvRuntime,
            tvGenres, tvDirector, tvActors, tvEpisodeLabel, tvEpisode;
    private ImageView imgBanner, imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Objects.requireNonNull(getSupportActionBar()).hide();

        bindView();
        showEpisodeText();
        showDataTvShow();
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
        tvEpisode = findViewById(R.id.tv_episode);
        tvEpisodeLabel = findViewById(R.id.tv_episode_label);
        imgBanner = findViewById(R.id.img_banner);
        imgPoster = findViewById(R.id.img_poster);
    }

    private void showEpisodeText() {
        tvEpisode.setVisibility(View.VISIBLE);
        tvEpisodeLabel.setVisibility(View.VISIBLE);
    }

    private void showDataTvShow() {
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

//        tvTitle.setText(tvShow.getTitle());
//        tvRate.setText(tvShow.getRate());
//        tvDate.setText(tvShow.getDate());
//        tvOverview.setText(tvShow.getOverview());
//        tvRuntime.setText(tvShow.getRuntime());
//        tvGenres.setText(tvShow.getGenres());
//        tvDirector.setText(tvShow.getCreator());
//        tvActors.setText(tvShow.getActors());
//        tvEpisode.setText(tvShow.getEpisode());
//
//        Glide.with(this)
//                .load(tvShow.getPoster())
//                .centerCrop()
//                .into(imgBanner);
//
//        Glide.with(this)
//                .load(tvShow.getPoster())
//                .centerCrop()
//                .into(imgPoster);
    }
}
