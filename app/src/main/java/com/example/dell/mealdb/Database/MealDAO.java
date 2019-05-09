package com.example.dell.mealdb.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MealDAO {
    @Query("select * from FavuoriteMeal")
    LiveData<List<FavuoriteMeal>>  getAllData();

    @Insert
    void insert(FavuoriteMeal favuoriteMeal);

    @Delete
    void deleteData(FavuoriteMeal favuoriteMeal);
}
