package com.didik.mcd.presentation.movie.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.didik.mcd.R;
import com.didik.mcd.model.Movie;
import com.didik.mcd.presentation.movie.detail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MovieFragment extends Fragment implements MovieListener {

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerViewMovies;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private Observer<List<Movie>> moviesObserver = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            if (movies != null) {
                movieList.addAll(movies);
                movieAdapter.notifyDataSetChanged();
            }
        }
    };
    private Observer<Boolean> loadingObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean state) {
            if (state) progressBar.setVisibility(View.VISIBLE);
            else progressBar.setVisibility(View.GONE);
        }
    };
    private Observer<String> errorMessageObserver = new Observer<String>() {
        @Override
        public void onChanged(String message) {
            showErrorMessage(message);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setUpRecyclerView();
        setUpViewModel();
    }

    private void bindView(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerViewMovies = view.findViewById(R.id.rv_movies);
        recyclerViewMovies.setHasFixedSize(true);
    }

    private void setUpRecyclerView() {
        movieAdapter = new MovieAdapter(movieList, this);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMovies.setAdapter(movieAdapter);
    }

    private void setUpViewModel() {
        MovieViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(MovieViewModel.class);

        viewModel.getMovies().observe(this, moviesObserver);
        viewModel.getStateLoading().observe(this, loadingObserver);
        viewModel.getErrorMessage().observe(this, errorMessageObserver);

        viewModel.fetchMovies();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMovieClicked(int position) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieList.get(position).getId());
        startActivity(intent);
    }
}
