package com.didik.mcd.data.database;

import com.didik.mcd.data.dao.FavoriteDao;
import com.didik.mcd.data.entity.Favorite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Favorite.class, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao getFavoriteDao();
}
