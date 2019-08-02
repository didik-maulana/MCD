package com.didik.mcd.presentation.movie.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didik.mcd.R;
import com.didik.mcd.model.Movie;

import java.util.List;

class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movie> movieList;
    private MovieListener mItemListener;

    MovieAdapter(List<Movie> movieList, MovieListener mItemListener) {
        this.movieList = movieList;
        this.mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MovieViewHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int position) {
        viewHolder.bindItem(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
