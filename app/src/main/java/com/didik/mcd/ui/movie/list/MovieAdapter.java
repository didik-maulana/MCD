package com.didik.mcd.ui.movie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.entity.Movie;
import com.didik.mcd.helper.ItemClickable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private ItemClickable mItemListener;

    public MovieAdapter(List<Movie> movieList, ItemClickable mItemListener) {
        this.movieList = movieList;
        this.mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
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

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMoviePoster;
        private TextView tvMovieTitle;
        private TextView tvMovieRate;

        MovieViewHolder(View view, final ItemClickable mItemListener) {
            super(view);

            tvMovieTitle = view.findViewById(R.id.tv_title);
            tvMovieRate = view.findViewById(R.id.tv_rate);
            imgMoviePoster = view.findViewById(R.id.img_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        void bindItem(Movie movie) {
            tvMovieTitle.setText(movie.getTitle());
            tvMovieRate.setText(String.valueOf(movie.getVoteAverage()));

            String imageUrl = BuildConfig.IMAGE_URL + movie.getPosterPath();
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(imgMoviePoster);
        }
    }
}
