package com.didik.mcd.ui.tv.list;

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
import com.didik.mcd.data.entity.TvShow;
import com.didik.mcd.helper.ItemClickable;
import com.didik.mcd.ui.tv.detail.TvShowDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TvShowFragment extends Fragment implements ItemClickable {

    private List<TvShow> tvShowList = new ArrayList<>();
    private RecyclerView recyclerViewTvShow;
    private ProgressBar progressBar;
    private TvShowAdapter tvShowAdapter;
    private TvShowViewModel viewModel;

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
        viewModel.fetchTvShows();
    }

    private void bindView(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerViewTvShow = view.findViewById(R.id.rv_movies);
        recyclerViewTvShow.setHasFixedSize(true);
    }

    private void setupRecyclerView() {
        tvShowAdapter = new TvShowAdapter(tvShowList, this);
        recyclerViewTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTvShow.setAdapter(tvShowAdapter);
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(TvShowViewModel.class);

        viewModel.getTvShows().observe(this, new Observer<List<TvShow>>() {
            @Override
            public void onChanged(List<TvShow> tvShows) {
                if (tvShows != null) {
                    tvShowList.addAll(tvShows);
                    tvShowAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getStateLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean state) {
                if (state)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.GONE);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), TvShowDetailActivity.class);
        intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, tvShowList.get(position).getId());
        startActivity(intent);
    }
}
