package com.didik.mcd.data.dao;

import com.didik.mcd.data.entity.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Movie> getAllMovie();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
