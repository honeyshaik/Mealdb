package com.example.dell.mealdb.Database;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dell.mealdb.ListActivity;
import com.example.dell.mealdb.MainActivity;
import com.example.dell.mealdb.R;
import com.example.dell.mealdb.MainActivity;


import java.util.List;

public  class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    MainActivity context;
    List<FavuoriteMeal> mealList;

    public FavouriteAdapter(MainActivity mainActivity, List<FavuoriteMeal> favuoriteMeal) {
      context=mainActivity;
      mealList=favuoriteMeal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.favuorite,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(mealList.get(i).getStrMealThumb()).into(viewHolder.favimg);

    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView favimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favimg=itemView.findViewById(R.id.fav_movies);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,ListActivity.class);
            intent.putExtra("key",mealList.get(getAdapterPosition()).getIdMeal());
            intent.putExtra("title",mealList.get(getAdapterPosition()).getStrMeal());
            intent.putExtra("instrctions",mealList.get(getAdapterPosition()).getStrInstructions());
            intent.putExtra("image", (String) mealList.get(getAdapterPosition()).getStrMealThumb());
            context.startActivity(intent);
        }
    }
}
