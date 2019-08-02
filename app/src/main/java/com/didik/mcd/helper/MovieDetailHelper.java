package com.didik.mcd.helper;

import com.didik.mcd.model.Genre;
import com.didik.mcd.model.Production;

import java.util.List;

public class MovieDetailHelper {
    public String getProductions(List<Production> productionList) {
        StringBuilder productions = new StringBuilder();
        for (int i = 0; i < productionList.size(); i++) {
            if (i != productionList.size() - 1)
                productions.append(productionList.get(i).getName()).append(", ");
            else productions.append(productionList.get(i).getName());
        }
        return productions.toString();
    }

    public String getGenres(List<Genre> genreList) {
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < genreList.size(); i++) {
            if (i != genreList.size() - 1) genres.append(genreList.get(i).getName()).append(", ");
            else genres.append(genreList.get(i).getName());
        }
        return genres.toString();
    }

    public String getRuntime(String runtime) {
        if (runtime == null) return "-";
        else return runtime + "m";
    }

    public String getEpisode(Integer episode) {
        if (episode == null) return "-";
        else return String.valueOf(episode);
    }
}
