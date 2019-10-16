package com.didik.mcd.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.didik.mcd.data.dao.FavoriteDao;
import com.didik.mcd.data.entity.Favorite;

@Database(entities = Favorite.class, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao getFavoriteDao();
}
