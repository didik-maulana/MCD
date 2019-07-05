package com.didik.mcd.presentation.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didik.mcd.R;
import com.didik.mcd.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movies;

    MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int index) {
        return movies.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_movie_layout, parent, false);
        }

        MovieViewHolder viewHolder = new MovieViewHolder(view);
        Movie movie = (Movie) getItem(index);
        viewHolder.bindMovie(movie);
        return view;
    }
}
