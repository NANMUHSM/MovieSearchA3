package com.example.moviesearcha3.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesearcha3.model.FavoriteMovie;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieViewModel extends ViewModel {
    final MutableLiveData<List<FavoriteMovie>> favoriteMovies = new MutableLiveData<>();

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LiveData<List<FavoriteMovie>> getFavoriteMovies(){
        return favoriteMovies;
    }
    public void loadFavorites() {
        FirebaseFirestore.getInstance().collection("favorites")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<FavoriteMovie> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        FavoriteMovie movie = doc.toObject(FavoriteMovie.class);
                        list.add(movie);
                    }
                    favoriteMovies.setValue(list); // update UI
                })
                .addOnFailureListener(e -> {

                });
    }
}
