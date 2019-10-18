package com.didik.mcd.ui.favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didik.mcd.R;
import com.didik.mcd.data.database.DatabaseClient;
import com.didik.mcd.data.entity.Favorite;
import com.didik.mcd.data.entity.TvShow;
import com.didik.mcd.helper.ItemClickable;
import com.didik.mcd.ui.tv.detail.TvShowDetailActivity;
import com.didik.mcd.ui.tv.list.TvShowAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteTvShowFragment extends Fragment implements ItemClickable {
    private RecyclerView recyclerView;
    private List<TvShow> tvShowList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFavoriteTvShow();
        setupRecyclerView(view);
    }

    private void getFavoriteTvShow() {
        GetFavoriteTvShowTask favoriteTvShowTask = new GetFavoriteTvShowTask(this);
        favoriteTvShowTask.execute();
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rv_movies);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), TvShowDetailActivity.class);
        intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, tvShowList.get(position).getId());
        startActivity(intent);
    }

    public static class GetFavoriteTvShowTask extends AsyncTask<Void, Void, List<Favorite>> {
        private WeakReference<FavoriteTvShowFragment> fragment;

        GetFavoriteTvShowTask(FavoriteTvShowFragment fragment) {
            this.fragment = new WeakReference<>(fragment);
        }

        @Override
        protected List<Favorite> doInBackground(Void... voids) {
            String favoriteKey = fragment.get().getContext().getString(R.string.key_tv_shows);
            return DatabaseClient
                    .getInstance(fragment.get().getContext())
                    .getAppDatabase()
                    .getFavoriteDao()
                    .getFavoriteList(favoriteKey);
        }

        @Override
        protected void onPostExecute(List<Favorite> favorites) {
            super.onPostExecute(favorites);
            List<TvShow> tvShowList = new ArrayList<>();
            for (int i = 0; i < favorites.size(); i++) {
                TvShow tvShow = new TvShow();
                tvShow.setId(favorites.get(i).getId());
                tvShow.setName(favorites.get(i).getTitle());
                tvShow.setVoteAverage(Double.parseDouble(favorites.get(i).getVoteAverage()));
                tvShow.setPosterPath(favorites.get(i).getPosterPath());
                tvShowList.add(tvShow);
            }
            fragment.get().tvShowList.addAll(tvShowList);
            fragment.get().recyclerView
                    .setLayoutManager(new LinearLayoutManager(fragment.get().getActivity()));
            fragment.get().recyclerView.setAdapter(new TvShowAdapter(tvShowList, fragment.get()));
        }
    }
}
