package com.didik.mcd.ui.tv.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.entity.TvShow;
import com.didik.mcd.helper.ItemClickable;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private List<TvShow> tvShows;
    private ItemClickable mItemListener;

    public TvShowAdapter(List<TvShow> tvShows, ItemClickable mItemListener) {
        this.tvShows = tvShows;
        this.mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
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

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvTitle;
        private TextView tvRate;

        TvShowViewHolder(@NonNull View view, final ItemClickable mItemListener) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvRate = view.findViewById(R.id.tv_rate);
            imgPoster = view.findViewById(R.id.img_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onItemClicked(getAdapterPosition());
                }
            });
        }

        void bindItem(TvShow tvShow) {
            tvTitle.setText(tvShow.getName());
            tvRate.setText(String.valueOf(tvShow.getVoteAverage()));

            String imageUrl = BuildConfig.IMAGE_URL + tvShow.getPosterPath();
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(imgPoster);
        }
    }
}
