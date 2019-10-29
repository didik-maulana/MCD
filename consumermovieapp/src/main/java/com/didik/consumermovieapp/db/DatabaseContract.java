package com.didik.consumermovieapp.db;

import android.net.Uri;

public class DatabaseContract {
    public static final String AUTHORITY = "com.didik.mcd.provider";
    private static final String SCHEME = "content";

    public static final class FavoriteColumns {
        public static final String TABLE_NAME = "favorites";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String POSTER_PATH = "poster_path";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String TYPE = "type";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

    }
}
