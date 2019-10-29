package com.didik.consumermovieapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didik.consumermovieapp.BuildConfig;
import com.didik.consumermovieapp.R;
import com.didik.consumermovieapp.model.Favorite;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MovieViewHolder> {

    private ArrayList<Favorite> favorites;

    public void setFavorites(ArrayList<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int position) {
        viewHolder.bindItem(favorites.get(position));
    }

    @Override
    public int getItemCount() {
        if (favorites == null) {
            Log.d("Cuuursor size", String.valueOf(favorites));
        } else {
            Log.d("Cuuursor size", "tidak null" + favorites.size());
        }
        return favorites == null ? 0 : favorites.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMoviePoster;
        private TextView tvMovieTitle;
        private TextView tvMovieRate;

        MovieViewHolder(View view) {
            super(view);
            tvMovieTitle = view.findViewById(R.id.tv_title);
            tvMovieRate = view.findViewById(R.id.tv_rate);
            imgMoviePoster = view.findViewById(R.id.img_poster);
        }

        void bindItem(Favorite favorite) {
            tvMovieTitle.setText(favorite.getTitle());
            tvMovieRate.setText(favorite.getVoteAverage());

            String imageUrl = BuildConfig.IMAGE_URL + favorite.getPosterPath();
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(imgMoviePoster);
        }
    }
}
