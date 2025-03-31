package com.example.moviesearcha3.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("Search")
    private List<com.example.moviesearcha3.model.Movie> movies;

    @SerializedName("Response")
    private  String response;

    @SerializedName("totalResults")
    private String totalResults;

    public List<com.example.moviesearcha3.model.Movie> getMovies() {
        return movies;
    }

    public String getResponse() {
        return response;
    }

    public String getTotalResults() {
        return totalResults;
    }
}
