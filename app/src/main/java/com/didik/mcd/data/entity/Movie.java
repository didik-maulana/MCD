package com.didik.mcd.data.entity;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @SerializedName("id")
    @PrimaryKey()
    private Integer id;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "isFavorite")
    private Boolean isFavorite = false;

    public Movie(Integer id,
                 String title,
                 String posterPath,
                 Double voteAverage,
                 Boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.isFavorite = isFavorite;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
}
