package com.didik.mcd.data.database;

import android.content.Context;

import com.didik.mcd.data.dao.MovieDao;
import com.didik.mcd.data.dao.TvShowDao;
import com.didik.mcd.data.entity.Movie;
import com.didik.mcd.data.entity.TvShow;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        Movie.class,
        TvShow.class
}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "movie_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract MovieDao getMovieDao();

    public abstract TvShowDao getTvShowDao();
}
