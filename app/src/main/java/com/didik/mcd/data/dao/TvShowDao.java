package com.didik.mcd.data.dao;

import com.didik.mcd.data.entity.TvShow;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TvShowDao {
    @Query("SELECT * FROM tv_show")
    List<TvShow> getAllTvShow();

    @Insert
    void insertTvShow(TvShow tvShow);

    @Delete
    void deleteTvShow(TvShow tvShow);
}
