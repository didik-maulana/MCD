package com.didik.mcd.data.entity;

import com.didik.mcd.helper.DateFormatter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetail {
    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private String id;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<Production> productionCompanies;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private String voteAverage;

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getGenres() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            if (i != genres.size() - 1) {
                stringBuilder.append(genres.get(i).getName()).append(", ");
            } else {
                stringBuilder.append(genres.get(i).getName());
            }
        }
        return stringBuilder.toString();
    }

    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getProductionCompanies() {
        StringBuilder productions = new StringBuilder();
        for (int i = 0; i < productionCompanies.size(); i++) {
            if (i != productionCompanies.size() - 1) {
                productions.append(productionCompanies.get(i).getName()).append(", ");
            } else {
                productions.append(productionCompanies.get(i).getName());
            }
        }
        return productions.toString();
    }

    public String getReleaseDate() {
        return new DateFormatter().formatDate(releaseDate);
    }

    public String getRuntime() {
        if (runtime == null) {
            return "-";
        } else {
            return runtime + "m";
        }
    }

    public String getTitle() {
        return title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }
}
