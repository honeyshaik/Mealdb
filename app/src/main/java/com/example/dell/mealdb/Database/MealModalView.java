package com.example.dell.mealdb.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MealModalView extends AndroidViewModel {
    LiveData<List<FavuoriteMeal>> listLiveData;
    FavuoriteMeal favuoriteMeal;
    MealRepositary repositary;

    public MealModalView(@NonNull Application application) {
        super(application);
        repositary = new MealRepositary(application);
        listLiveData = repositary.getData();
    }

    public LiveData<List<FavuoriteMeal>> getListLiveData() {
        return listLiveData;
    }

    public void insertList(FavuoriteMeal favuoriteMeal) {
        repositary.insert(favuoriteMeal);
    }

    public void deletelist(FavuoriteMeal favuoriteMeal1) {
           repositary.delete(favuoriteMeal1);
    }
}