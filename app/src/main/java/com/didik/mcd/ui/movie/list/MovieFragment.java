package com.didik.mcd.ui.movie.list;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.didik.mcd.R;
import com.didik.mcd.data.entity.Movie;
import com.didik.mcd.helper.ItemClickable;
import com.didik.mcd.ui.movie.detail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MovieFragment extends Fragment implements ItemClickable {

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerViewMovies;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private MovieViewModel viewModel;
    private EditText edtSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupViewModel();
        setupRecyclerView();
        setupSearchListener();
        viewModel.fetchMovies();
    }

    private void bindView(View view) {
        edtSearch = view.findViewById(R.id.edt_search);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerViewMovies = view.findViewById(R.id.rv_movies);
        recyclerViewMovies.setHasFixedSize(true);
    }

    private void setupRecyclerView() {
        movieAdapter = new MovieAdapter(movieList, this);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMovies.setAdapter(movieAdapter);
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(MovieViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieList.clear();
                if (movies != null) {
                    movieList.addAll(movies);
                    movieAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getStateLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean state) {
                if (state) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
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

    private void setupSearchListener() {
        edtSearch.setVisibility(View.VISIBLE);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                String query = text.toString();
                if (query.trim().length() > 3) {
                    viewModel.fetchSearchMovies(query);
                } else if (query.trim().length() == 0) {
                    viewModel.fetchMovies();
                }
            }
        });
    }

    private void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieList.get(position).getId());
        startActivity(intent);
    }
}
