package com.example.moviesearcha3.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.moviesearcha3.databinding.ActivityFavoriteDetailsBinding;
import com.example.moviesearcha3.model.FavoriteMovie;
import com.example.moviesearcha3.viewmodel.FavoriteDetailsViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavoriteDetailsActivity extends AppCompatActivity {
    ActivityFavoriteDetailsBinding binding;
    FavoriteDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get imdbID
        String imdbID = getIntent().getStringExtra("imdbID");
        if(imdbID == null){
            Toast.makeText(this,"No movie selected",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        //initial viewModel
        viewModel = new ViewModelProvider(this).get(FavoriteDetailsViewModel.class);
        viewModel.loadFavoriteMovie(imdbID);

        //update liveData
        viewModel.getMovie().observe(this,movie -> {
            if(movie != null){
                binding.titleTxt.setText(movie.getTitle());
                binding.ratingText.setText("Rated: " +movie.getRated());
                binding.descriptionEdit.setText(movie.getDescription());
                Glide.with(this).load(movie.getPoster()).into(binding.imageview);
            }
        });

        //return button
        binding.backBtn.setOnClickListener(v -> {finish();});

        //update button
        binding.updateBtn.setOnClickListener(v -> {
            String newDescription = binding.descriptionEdit.getText().toString().trim();
            if(!newDescription.isEmpty()){
                viewModel.updateDescription(
                        newDescription,
                        () -> Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show(),
                        () -> Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                );
            } else {
                Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        //delete button
        binding.deleteBtn.setOnClickListener(v -> {
            viewModel.deleteMovie(
                    () -> {Toast.makeText(this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                    finish();
                    },
                    () -> Toast.makeText(this,"Delete failed",Toast.LENGTH_LONG).show()
            );
        });

        // back button
        binding.backBtn.setOnClickListener(v -> finish());


    }

}
