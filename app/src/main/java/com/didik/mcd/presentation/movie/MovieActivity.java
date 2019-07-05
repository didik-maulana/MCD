package com.didik.mcd.presentation.movie;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.didik.mcd.R;
import com.didik.mcd.model.Movie;
import com.didik.mcd.presentation.detail.MovieDetailActivity;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    private String[] title, rate, date, overview, runtime, genres, director, actors;
    private TypedArray poster;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ListView lvMovies = findViewById(R.id.lv_movies);
        movieAdapter = new MovieAdapter(this);
        lvMovies.setAdapter(movieAdapter);

        getResourcesMovie();
        setDataMovie();

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movies.get(i));
                startActivity(intent);
            }
        });
    }

    private void getResourcesMovie() {
        title = getResources().getStringArray(R.array.movies_title);
        rate = getResources().getStringArray(R.array.movies_rate);
        date = getResources().getStringArray(R.array.movies_date);
        overview = getResources().getStringArray(R.array.movies_overview);
        runtime = getResources().getStringArray(R.array.movies_runtime);
        genres = getResources().getStringArray(R.array.movies_genres);
        director = getResources().getStringArray(R.array.movies_director);
        actors = getResources().getStringArray(R.array.movies_actors);
        poster = getResources().obtainTypedArray(R.array.movies_poster);
    }

    private void setDataMovie() {
        movies = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            Movie movie = new Movie();
            movie.setTitle(title[i]);
            movie.setRate(rate[i]);
            movie.setDate(date[i]);
            movie.setOverview(overview[i]);
            movie.setRuntime(runtime[i]);
            movie.setGenres(genres[i]);
            movie.setDirector(director[i]);
            movie.setActors(actors[i]);
            movie.setPoster(poster.getResourceId(i, -1));

            movies.add(movie);
        }
        movieAdapter.setMovies(movies);
    }
}
