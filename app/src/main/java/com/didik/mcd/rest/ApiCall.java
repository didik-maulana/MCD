package com.didik.mcd.rest;

import com.didik.mcd.model.MovieResponse;
import com.didik.mcd.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCall {
    @GET("discover/movie")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey,
                                  @Query("language") String language);

    @GET("discover/tv")
    Call<TvShowResponse> getTvShows(@Query("api_key") String apiKey,
                                    @Query("language") String language);
}
