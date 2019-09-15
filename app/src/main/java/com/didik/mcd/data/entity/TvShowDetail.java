package com.didik.mcd.data.entity;

import com.didik.mcd.helper.DateFormatter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowDetail {
    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("episode_run_time")
    private List<Integer> episodeRuntime;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("number_of_episode")
    private Integer numberOfEpisode;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<Production> productionCompanies;

    @SerializedName("vote_average")
    private Double voteAverage;

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getEpisodeRuntime() {
        if (episodeRuntime.get(0) == null) {
            return "-";
        } else {
            return String.valueOf(episodeRuntime.get(0));
        }
    }

    public String getFirstAirDate() {
        return new DateFormatter().formatDate(firstAirDate);
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumberOfEpisode() {
        if (numberOfEpisode == null) {
            return "-";
        } else {
            return String.valueOf(numberOfEpisode);
        }
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

    public String getVoteAverage() {
        return String.valueOf(voteAverage);
    }
}
