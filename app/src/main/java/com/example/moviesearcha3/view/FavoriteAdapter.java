package com.example.moviesearcha3.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesearcha3.databinding.ItemLayoutBinding;
import com.example.moviesearcha3.model.FavoriteMovie;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    List<FavoriteMovie> favoriteMovieList;
    OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(FavoriteMovie movie);
    }

    //constructor
    public FavoriteAdapter(List<FavoriteMovie> favoriteMovieList, OnItemClickListener listener) {
        this.favoriteMovieList = favoriteMovieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        ItemLayoutBinding binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FavoriteViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.bind(favoriteMovieList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return favoriteMovieList.size();
    }

    public void setFavoriteMovieList(List<FavoriteMovie> favoriteMovieList) {
        this.favoriteMovieList = favoriteMovieList;
        notifyDataSetChanged();
    }

}
