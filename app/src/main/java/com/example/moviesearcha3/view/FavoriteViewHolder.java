package com.example.moviesearcha3.view;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesearcha3.databinding.ItemLayoutBinding;
import com.example.moviesearcha3.model.FavoriteMovie;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    ItemLayoutBinding binding;

    public  FavoriteViewHolder(ItemLayoutBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(FavoriteMovie movie,FavoriteAdapter.OnItemClickListener listener){
        binding.titleTxt.setText(movie.getTitle());
        binding.descriptionText.setText(movie.getDescription());
        binding.typeText.setText("Type: "+ movie.getType());

        Glide.with(binding.getRoot().getContext())
                .load(movie.getPoster())
                .into(binding.imageview);
        binding.getRoot().setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClick(movie);
            }
        });
    }
}
