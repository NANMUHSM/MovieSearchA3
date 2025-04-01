package com.example.moviesearcha3.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviesearcha3.databinding.ActivityMovieFavoriteBinding;
import com.example.moviesearcha3.model.FavoriteMovie;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.moviesearcha3.view.FavoriteAdapter;

import java.util.ArrayList;

public class FavoriteMovieActivity extends AppCompatActivity {

    ActivityMovieFavoriteBinding binding;
    FavoriteAdapter favoriteAdapter;
    ArrayList<FavoriteMovie> favoriteMovieArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        binding = ActivityMovieFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initial list
        favoriteMovieArrayList = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(favoriteMovieArrayList, movie -> {
            Intent intent = new Intent(FavoriteMovieActivity.this, FavoriteDetailsActivity.class);
            intent.putExtra("imdbID", movie.getImdbID());
            startActivity(intent);
        });


        binding.favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.favoriteRecyclerView.setAdapter(favoriteAdapter);

        loadFavoriteFromFirestore();

        //Tab button
        binding.tabSearch.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        });
        binding.tabFavorites.setOnClickListener(v -> {

        });
    }
    private  void loadFavoriteFromFirestore(){
        FirebaseFirestore.getInstance().collection("favorites")
                .get()
                .addOnSuccessListener(querySnapshot ->{
                    favoriteMovieArrayList.clear();

                    for(QueryDocumentSnapshot doc : querySnapshot){
                        FavoriteMovie movie = doc.toObject(FavoriteMovie.class);
                        favoriteMovieArrayList.add(movie);
                    }

                    // adapter update UI
                    favoriteAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this,"loaded failed: " +e.getMessage(),Toast.LENGTH_LONG).show();
                });
    }
}
