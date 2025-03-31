package com.example.moviesearcha3.view;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesearcha3.R;
import com.example.moviesearcha3.databinding.ItemLayoutBinding;
import com.example.moviesearcha3.model.Movie;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ItemLayoutBinding binding;


    public MyViewHolder(ItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Movie movie, MyAdapter.OnItemClickListener listener) {
        binding.titleTxt.setText(movie.getTitle());
        binding.descriptionText.setText(movie.getDescription());
        binding.typeText.setText("Type: " + movie.getType());

        Glide.with(binding.getRoot().getContext())
                .load(movie.getPoster())
                .into(binding.imageview);

        binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(movie);
            }
        });
    }
}