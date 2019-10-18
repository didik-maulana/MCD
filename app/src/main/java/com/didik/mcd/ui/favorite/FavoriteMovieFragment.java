package com.didik.mcd.ui.favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didik.mcd.R;
import com.didik.mcd.data.database.DatabaseClient;
import com.didik.mcd.data.entity.Favorite;
import com.didik.mcd.data.entity.Movie;
import com.didik.mcd.helper.ItemClickable;
import com.didik.mcd.ui.movie.detail.MovieDetailActivity;
import com.didik.mcd.ui.movie.list.MovieAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteMovieFragment extends Fragment implements ItemClickable {
    private RecyclerView recyclerView;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFavoriteMovie();
        setupRecyclerView(view);
    }

    private void getFavoriteMovie() {
        GetFavoriteMovieTask favoriteMovieTask = new GetFavoriteMovieTask(this);
        favoriteMovieTask.execute();
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rv_movies);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieList.get(position).getId());
        startActivity(intent);
    }

    public static class GetFavoriteMovieTask extends AsyncTask<Void, Void, List<Favorite>> {
        private WeakReference<FavoriteMovieFragment> fragment;

        GetFavoriteMovieTask(FavoriteMovieFragment fragment) {
            this.fragment = new WeakReference<>(fragment);
        }

        @Override
        protected List<Favorite> doInBackground(Void... voids) {
            String favoriteKey = fragment.get().getContext().getString(R.string.key_movies);
            return DatabaseClient
                    .getInstance(fragment.get().getContext())
                    .getAppDatabase()
                    .getFavoriteDao()
                    .getFavoriteList(favoriteKey);
        }

        @Override
        protected void onPostExecute(List<Favorite> favorites) {
            super.onPostExecute(favorites);
            List<Movie> movieList = new ArrayList<>();
            for (int i = 0; i < favorites.size(); i++) {
                Movie movie = new Movie();
                movie.setId(favorites.get(i).getId());
                movie.setTitle(favorites.get(i).getTitle());
                movie.setVoteAverage(Double.parseDouble(favorites.get(i).getVoteAverage()));
                movie.setPosterPath(favorites.get(i).getPosterPath());
                movieList.add(movie);
            }
            fragment.get().movieList.addAll(movieList);
            fragment.get().recyclerView
                    .setLayoutManager(new LinearLayoutManager(fragment.get().getActivity()));
            fragment.get().recyclerView.setAdapter(new MovieAdapter(movieList, fragment.get()));
        }
    }
}
