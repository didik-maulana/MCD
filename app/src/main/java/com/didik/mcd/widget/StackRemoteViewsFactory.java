package com.didik.mcd.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.database.DatabaseClient;
import com.didik.mcd.data.entity.Favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;
    private int widgetId;
    private List<Favorite> favorites = new ArrayList<>();

    StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public void onDataSetChanged() {
        favorites = DatabaseClient.getInstance(context)
                .getAppDatabase()
                .getFavoriteDao()
                .getFavoriteList("movies");
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews =
                new RemoteViews(context.getPackageName(), R.layout.movie_widget_item);
        if (!favorites.isEmpty()) {
            Favorite favorite = favorites.get(position);
            try {
                Bitmap imageView = Glide.with(context)
                        .asBitmap()
                        .load(BuildConfig.IMAGE_URL + favorite.getPosterPath())
                        .centerCrop()
                        .submit()
                        .get();
                remoteViews.setImageViewBitmap(R.id.imageView, imageView);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putInt(FavoriteWidget.EXTRA_ITEM, position);
            bundle.putInt(FavoriteWidget.EXTRA_MOVIE_ID, favorite.getId());

            Intent intent = new Intent();
            intent.putExtras(bundle);
            remoteViews.setOnClickFillInIntent(R.id.containerView, intent);
        }

        return remoteViews;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
}
