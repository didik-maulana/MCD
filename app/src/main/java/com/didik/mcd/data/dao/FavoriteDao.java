package com.didik.mcd.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.didik.mcd.data.entity.Favorite;

import java.util.List;

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
}
