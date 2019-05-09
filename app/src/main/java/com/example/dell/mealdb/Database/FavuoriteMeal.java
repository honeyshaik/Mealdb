package com.example.dell.mealdb.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "FavuoriteMeal")
public class FavuoriteMeal implements Serializable{
    @PrimaryKey
    @NonNull
    @SerializedName("strMeal")
    @Expose
    private String strMeal;

    @SerializedName("idMeal")
    @Expose
    private String idMeal;

    @SerializedName("strInstructions")
    @Expose
    private String strInstructions;
    @SerializedName("strMealThumb")
    @Expose
    private String strMealThumb;


    @NonNull
    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(@NonNull String strMeal) {
        this.strMeal = strMeal;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public FavuoriteMeal(@NonNull String strMeal, String idMeal, String strInstructions, String strMealThumb) {
        this.strMeal = strMeal;
        this.idMeal = idMeal;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
    }
}
