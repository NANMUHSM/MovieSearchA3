package com.example.moviesearcha3.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.moviesearcha3.R;
import com.example.moviesearcha3.databinding.ItemDetailLayoutBinding;
import com.example.moviesearcha3.model.FavoriteMovie;
import com.example.moviesearcha3.model.Movie;
import com.example.moviesearcha3.viewmodel.MovieViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String API_KEY = "d40b7945";
    private MovieViewModel movieViewModel;
    private ItemDetailLayoutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding UI
        binding = ItemDetailLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(v -> finish());


        //initial viewModel
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        //get imdbID
        String imdbID = getIntent().getStringExtra("imdbID");
        Log.d("MovieDetailsActivity", "Received imdbID: " + imdbID);
        if(imdbID != null){
            movieViewModel.getMovieDetails(imdbID,API_KEY);
        }

        // save current movie to favorite list
        final Movie[] currentMovie = new Movie[1];

        //liveData and update UI
        movieViewModel.getMovieDetail().observe(this,movie -> {
            if(movie != null){
                currentMovie[0] = movie;

                binding.titleTxt.setText(movie.getTitle());
                binding.descriptionText.setText(movie.getDescription());
                binding.ratingText.setText("Rated: " +movie.getRated());
                Glide.with(this).load(movie.getPoster()).into(binding.imageview);
            }
        });

        // add movie to firestore
        binding.addFavoriteBtn.setOnClickListener(v -> {
            if(currentMovie[0] == null){
                Toast.makeText(this,"Movie data not loaded yet",Toast.LENGTH_SHORT).show();
                return;
            }

            FavoriteMovie favoriteMovie = new FavoriteMovie(
                    currentMovie[0].getTitle(),
                    currentMovie[0].getType(),
                    currentMovie[0].getPoster(),
                    currentMovie[0].getRated(),
                    currentMovie[0].getDescription(),
                    currentMovie[0].getImdbID()
            );

            // write to firestore
            FirebaseFirestore.getInstance()
                    .collection("favorites")
                    .document(currentMovie[0].getImdbID())
                    .set(favoriteMovie)
                    .addOnSuccessListener(a -> {
                        Toast.makeText(this,"Added to Favorite",Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this,"Failed to add: "+e.getMessage(),Toast.LENGTH_LONG).show();
                    });


        });



    }
}

