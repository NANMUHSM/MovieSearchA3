package com.example.moviesearcha3.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviesearcha3.databinding.ActivityMovieFavoriteBinding;
import com.example.moviesearcha3.model.FavoriteMovie;
import com.example.moviesearcha3.viewmodel.FavoriteMovieViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.moviesearcha3.view.FavoriteAdapter;

import java.util.ArrayList;

public class FavoriteMovieActivity extends AppCompatActivity {

    ActivityMovieFavoriteBinding binding;
    FavoriteAdapter favoriteAdapter;
    ArrayList<FavoriteMovie> favoriteMovieArrayList = new ArrayList<>();

    FavoriteMovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        binding = ActivityMovieFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initial viewModel
        viewModel = new ViewModelProvider(this).get(FavoriteMovieViewModel.class);


        //set RecyclerView and Adapter
        favoriteAdapter = new FavoriteAdapter(favoriteMovieArrayList, movie -> {
            Intent intent = new Intent(this,FavoriteDetailsActivity.class);
            intent.putExtra("imdbID", movie.getImdbID());
            startActivity(intent);
        });


        binding.favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.favoriteRecyclerView.setAdapter(favoriteAdapter);

        //update data
        viewModel.getFavoriteMovies().observe(this,movies ->{
            favoriteMovieArrayList.clear();
            favoriteMovieArrayList.addAll(movies);
            favoriteAdapter.notifyDataSetChanged();
        });

        viewModel.loadFavorites();

        //Tab button
        binding.tabSearch.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        });
        binding.tabFavorites.setOnClickListener(v -> {

        });

    }
}
