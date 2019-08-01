package com.didik.mcd.presentation.tv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didik.mcd.R;
import com.didik.mcd.model.TvShow;

import java.util.List;

class TvShowAdapter extends RecyclerView.Adapter<TvShowViewHolder> {

    private List<TvShow> tvShows;
    private TvShowListener mItemListener;

    TvShowAdapter(List<TvShow> tvShows, TvShowListener mItemListener) {
        this.tvShows = tvShows;
        this.mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new TvShowViewHolder(itemLayout, mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder viewHolder, int position) {
        viewHolder.bindItem(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }
}
