package com.didik.mcd.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.didik.mcd.data.database.DatabaseClient;

@SuppressLint("Registered")
public class MovieAppProvider extends ContentProvider {
    private static final int KEY_CODE_MOVIE = 1;
    private static final int KEY_CODE_TV_SHOW = 2;

    private static final String AUTHORITY = "com.didik.mcd.provider";
    private static final String KEY_FAVORITES = "favorites";
    private static final String KEY_MOVIES = "movies";
    private static final String KEY_TV_SHOW = "tv_shows";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, KEY_FAVORITES, KEY_CODE_MOVIE);
        URI_MATCHER.addURI(AUTHORITY, KEY_FAVORITES, KEY_CODE_TV_SHOW);
    }

    public MovieAppProvider() {
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)) {
            case KEY_CODE_MOVIE:
                cursor = DatabaseClient.getInstance(getContext()).getAppDatabase().getFavoriteDao()
                        .getFavoritesCursor(KEY_MOVIES);
                return cursor;
            case KEY_CODE_TV_SHOW:
                cursor = DatabaseClient.getInstance(getContext()).getAppDatabase().getFavoriteDao()
                        .getFavoritesCursor(KEY_TV_SHOW);
                return cursor;
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
