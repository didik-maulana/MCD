package com.didik.mcd.data.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private static final String DB_NAME = "movie_db";
    private static DatabaseClient INSTANCE;
    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        appDatabase = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                DB_NAME)
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseClient(context);
        }
        return INSTANCE;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
