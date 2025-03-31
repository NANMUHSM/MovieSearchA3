package com.example.moviesearcha3.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesearcha3.model.Movie;
import com.example.moviesearcha3.model.MovieResponse;
import com.example.moviesearcha3.utils.ApiClient;
import com.example.moviesearcha3.utils.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private static final String TAG = "API_RESPONSE";
    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<Movie> movieDetail = new MutableLiveData<>();

    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }
    public LiveData<Movie> getMovieDetail(){
        return  movieDetail;
    }

    //search movies
    public void searchMovies(String query,String apiKey){
        Call<MovieResponse> call = apiInterface.searchMovies(query, apiKey);

        Log.d(TAG, "Request URL: " + call.request().url().toString());

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getResponse().equals("True")){
                    movies.setValue(response.body().getMovies());
                    Log.d(TAG,"Search movie is successful" + response.body().getMovies().size()+"movies found");
                }else {
                    Log.e(TAG,"Search movie is failed" +response.message());
                    Log.e(TAG, "Error Body: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Log.e(TAG,"API request failed"+t.getMessage());
            }
        });
    }

    //get movie details
    public void getMovieDetails(String imdbID,String apiKey){
        Call<Movie> call = apiInterface.getMovieDetails(imdbID, apiKey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful() && response.body() != null){
                    movieDetail.setValue(response.body());
                    Log.d(TAG,"Get movie detail is successful" +response.body().getTitle());
                }else {
                    Log.e(TAG,"Get movie detail is failed" +response.message());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                Log.e(TAG,"API request failed",t);
            }
        });
    }
}

