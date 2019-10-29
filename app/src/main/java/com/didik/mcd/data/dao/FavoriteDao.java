package com.didik.mcd.data.dao;

import android.database.Cursor;

import com.didik.mcd.data.entity.Favorite;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE type=:type")
    List<Favorite> getFavoriteList(String type);

    @Query("SELECT isFavorite FROM favorites WHERE id=:id")
    Boolean getIsFavorite(Integer id);

    @Insert
    void addFavorite(Favorite favorite);

    @Query("DELETE FROM favorites WHERE id=:id")
    void removeFavorite(Integer id);

    @Query("SELECT * FROM favorites WHERE type=:type")
    Cursor getFavoritesCursor(String type);
}
