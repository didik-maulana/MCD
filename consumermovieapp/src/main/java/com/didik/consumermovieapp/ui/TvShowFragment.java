package com.didik.consumermovieapp.ui;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.didik.consumermovieapp.R;
import com.didik.consumermovieapp.adapter.FavoriteAdapter;
import com.didik.consumermovieapp.db.DatabaseContract;
import com.didik.consumermovieapp.helper.MappingHelper;
import com.didik.consumermovieapp.model.Favorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TvShowFragment extends Fragment implements LoadFavoritesCallback {
    private static final String KEY_TV_SHOWS = "tv_shows";
    private FavoriteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new FavoriteAdapter();
        setupRecyclerView(view);
        getFavoriteMovies();
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void getFavoriteMovies() {
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(
                DatabaseContract.FavoriteColumns.CONTENT_URI,
                true,
                myObserver);

        new LoadTvShowAsync(getContext(), this).execute();
    }

    @Override
    public void preExecute() {
    }

    @Override
    public void postExecute(ArrayList<Favorite> favorites) {
        if (favorites.size() > 0) {
            adapter.setFavorites(MappingHelper.filterFavorites(favorites, KEY_TV_SHOWS));
        } else {
            adapter.setFavorites(new ArrayList<Favorite>());
            Toast.makeText(getContext(), "Data Favorite Belum Tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<Favorite>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoritesCallback> weakCallback;

        private LoadTvShowAsync(Context context, LoadFavoritesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Favorite> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver()
                    .query(DatabaseContract.FavoriteColumns.CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> favorites) {
            super.onPostExecute(favorites);
            weakCallback.get().postExecute(favorites);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadTvShowAsync(context, (LoadFavoritesCallback) context).execute();

        }
    }
}
