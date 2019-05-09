package com.example.dell.mealdb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.mealdb.JsonData.Meal;
import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.List;

public class MyAdapter extends AnimatedRecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    List<Meal> mealList;
    public MyAdapter(MainActivity mainActivity, List<Meal> meals) {
        context=mainActivity;
        mealList=meals;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.image,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {
/*
    viewHolder.textView.setText(mealList.get(i).getStrMeal());
*/
        Glide.with(context).load(mealList.get(i).getStrMealThumb()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends AnimatedRecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ListActivity.class);
                    intent.putExtra("key",mealList.get(getAdapterPosition()).getIdMeal());
                    intent.putExtra("title",mealList.get(getAdapterPosition()).getStrMeal());
                    intent.putExtra("instrctions",mealList.get(getAdapterPosition()).getStrInstructions());
                    intent.putExtra("image", mealList.get(getAdapterPosition()).getStrMealThumb());
                    context.startActivity(intent);
                }
            });

        }
    }
}
