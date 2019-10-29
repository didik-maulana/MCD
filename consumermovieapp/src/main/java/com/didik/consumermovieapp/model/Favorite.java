package com.didik.consumermovieapp.model;

public class Favorite {
    private Integer id;

    private String title;

    private String posterPath;

    private String voteAverage;

    private Boolean isFavorite;

    private String type;

    public Favorite(Integer id, String title, String posterPath, String voteAverage,
                    Boolean isFavorite, String type) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.isFavorite = isFavorite;
        this.type = type;
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

    public String getVoteAverage() {
        return voteAverage;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public String getType() {
        return type;
    }
}
