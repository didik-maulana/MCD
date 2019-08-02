package com.didik.mcd.presentation.tv.list;

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
import com.didik.mcd.model.TvShow;
import com.didik.mcd.presentation.tv.detail.TvShowDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TvShowFragment extends Fragment implements TvShowListener {

    private List<TvShow> tvShowList = new ArrayList<>();
    private RecyclerView recyclerViewTvShow;
    private ProgressBar progressBar;
    private TvShowAdapter tvShowAdapter;
    private Observer<List<TvShow>> tvShowsObserver = new Observer<List<TvShow>>() {
        @Override
        public void onChanged(List<TvShow> tvShows) {
            if (tvShows != null) {
                tvShowList.addAll(tvShows);
                tvShowAdapter.notifyDataSetChanged();
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
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
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
        recyclerViewTvShow = view.findViewById(R.id.rv_tv_show);
        recyclerViewTvShow.setHasFixedSize(true);
    }

    private void setUpRecyclerView() {
        tvShowAdapter = new TvShowAdapter(tvShowList, this);
        recyclerViewTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTvShow.setAdapter(tvShowAdapter);
    }

    private void setUpViewModel() {
        TvShowViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(TvShowViewModel.class);

        viewModel.getTvShows().observe(this, tvShowsObserver);
        viewModel.getStateLoading().observe(this, loadingObserver);
        viewModel.getErrorMessage().observe(this, errorMessageObserver);

        viewModel.fetchTvShows();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTvShowClicked(int position) {
        Intent intent = new Intent(getActivity(), TvShowDetailActivity.class);
        intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, tvShowList.get(position).getId());
        startActivity(intent);
    }
}
