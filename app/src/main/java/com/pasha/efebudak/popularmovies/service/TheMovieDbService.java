package com.pasha.efebudak.popularmovies.service;

import com.pasha.efebudak.popularmovies.model.MovieVideoResult;
import com.pasha.efebudak.popularmovies.model.Result;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by efebudak on 28/09/15.
 */
public interface TheMovieDbService {

    @GET("/discover/movie")
    Result listMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("/movie/{id}/videos")
    MovieVideoResult listMovieTrailers(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("/movie/{id}/reviews")
    Result listMovieReviews(@Path("id") String id, @Query("api_key") String apiKey);

}
