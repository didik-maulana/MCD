package com.didik.mcd.data.entity;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tv_show")
public class TvShow {
    @SerializedName("id")
    @PrimaryKey
    private Integer id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    public TvShow(Integer id,
                  String name,
                  Double voteAverage,
                  String posterPath) {
        this.id = id;
        this.name = name;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
