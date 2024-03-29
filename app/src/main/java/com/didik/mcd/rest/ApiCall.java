package com.didik.mcd.rest;

import com.didik.mcd.data.entity.MovieDetail;
import com.didik.mcd.data.entity.MovieResponse;
import com.didik.mcd.data.entity.TvShowDetail;
import com.didik.mcd.data.entity.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {
    @GET("discover/movie")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey,
                                  @Query("language") String language);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovies(@Query("api_key") String apiKey,
                                        @Query("language") String language,
                                        @Query("query") String query);

    @GET("discover/tv")
    Call<TvShowResponse> getTvShows(@Query("api_key") String apiKey,
                                    @Query("language") String language);

    @GET("search/tv")
    Call<TvShowResponse> getSearchTvShows(@Query("api_key") String apiKey,
                                          @Query("language") String language,
                                          @Query("query") String query);

    @GET("movie/{id}")
    Call<MovieDetail> getMovieDetail(@Path("id") Integer id,
                                     @Query("api_key") String apiKey,
                                     @Query("language") String language);

    @GET("tv/{id}")
    Call<TvShowDetail> getTvShowDetail(@Path("id") Integer id,
                                       @Query("api_key") String apiKey,
                                       @Query("language") String language);

    @GET("discover/movie")
    Call<MovieResponse> getMovieRelease(@Query("api_key") String apiKey,
                                        @Query("primary_release_date.gte") String gte,
                                        @Query("primary_release_date.lte") String lte);
}
