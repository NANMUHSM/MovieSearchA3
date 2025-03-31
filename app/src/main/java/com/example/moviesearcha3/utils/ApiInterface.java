package com.example.moviesearcha3.utils;

import com.example.moviesearcha3.model.Movie;
import com.example.moviesearcha3.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //search movie
    @GET("/")
    Call<MovieResponse> searchMovies(
            @Query("s") String query,
            @Query("apikey") String apiKey
    );

    @GET("/")
    Call<Movie> getMovieDetails(
            @Query("i") String imdbID,
            @Query("apikey") String apikey
    );
}

