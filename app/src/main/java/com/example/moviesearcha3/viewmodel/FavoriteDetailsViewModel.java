package com.example.moviesearcha3.viewmodel;

import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesearcha3.model.FavoriteMovie;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavoriteDetailsViewModel extends ViewModel {
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final MutableLiveData<FavoriteMovie> movieMutableLiveData = new MutableLiveData<>();
    private String firestoreDocId;

    public LiveData<FavoriteMovie> getMovie(){
        return movieMutableLiveData;
    }

    public void loadFavoriteMovie(String imdbID){
        db.collection("favorites")
                .whereEqualTo("imdbID",imdbID)
                .limit(1)
                .get()
                .addOnSuccessListener(querySnapshot ->{
                    if(!querySnapshot.isEmpty()){
                        DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
                        FavoriteMovie movie = doc.toObject(FavoriteMovie.class);
                        firestoreDocId = doc.getId();
                        movieMutableLiveData.setValue(movie);
                    }
                });
    }
    public  void updateDescription(String newDescription,Runnable onSuccess,Runnable onFailure){
        if(firestoreDocId == null) {
            Log.e("UpdateError", "firestoreDocId is null. Can't update");
            onFailure.run();
            return;
        }
        db.collection("favorites").document(firestoreDocId)
                .update("description", newDescription)
                .addOnSuccessListener(a -> {
                    Log.d("UpdateSuccess", "Document updated successfully");
                    onSuccess.run();
                })
                .addOnFailureListener(e -> {
                    Log.e("UpdateFail", "Error: " + e.getMessage());
                    onFailure.run();
                });
    }


    public void deleteMovie(Runnable onSuccess,Runnable onFailure){
        if(firestoreDocId != null){
            db.collection("favorites").document(firestoreDocId)
                    .delete()
                    .addOnSuccessListener(a -> onSuccess.run())
                    .addOnFailureListener(e -> onFailure.run());
        }
    }


}
