package com.didik.consumermovieapp.helper;

import android.database.Cursor;

import com.didik.consumermovieapp.db.DatabaseContract;
import com.didik.consumermovieapp.model.Favorite;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Favorite> mapCursorToArrayList(Cursor favoritesCursor) {
        ArrayList<Favorite> favorites = new ArrayList<>();

        while (favoritesCursor != null && favoritesCursor.moveToNext()) {
            Integer id = favoritesCursor.getInt(favoritesCursor
                    .getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID));
            String title = favoritesCursor.getString(
                    favoritesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
            String posterPath = favoritesCursor.getString(favoritesCursor
                    .getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER_PATH));
            String voteAverage = favoritesCursor.getString(favoritesCursor
                    .getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.VOTE_AVERAGE));
            String type = favoritesCursor.getString(
                    favoritesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TYPE));
            favorites.add(new Favorite(id, title, posterPath, voteAverage, true, type));
        }

        return favorites;
    }

    public static ArrayList<Favorite> filterFavorites(ArrayList<Favorite> favorites, String type) {
        ArrayList<Favorite> filteredFavorites = new ArrayList<>();
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getType().equals(type)) {
                filteredFavorites.add(favorites.get(i));
            }
        }
        return filteredFavorites;
    }
}
