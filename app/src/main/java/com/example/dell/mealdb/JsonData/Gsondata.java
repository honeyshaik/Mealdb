package com.example.dell.mealdb.JsonData;

import java.util.List;

import com.example.dell.mealdb.JsonData.Meal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gsondata {

    @SerializedName("meals")
    @Expose
    private List<Meal> meals = null;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}
