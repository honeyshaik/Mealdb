package com.example.dell.mealdb.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MealRepositary {
    MealDAO mealDAO;
    LiveData<List<FavuoriteMeal>> list;

    public MealRepositary(Application application) {
        MyDatabase database=MyDatabase.getDatabase(application);
        mealDAO =database.mealDAO();
        list= mealDAO.getAllData();
    }
    LiveData<List<FavuoriteMeal>> getData(){
        return list;
    }

    public void insert(FavuoriteMeal recipie){
        new taskInsert(mealDAO).execute(recipie);
    }

    public void delete(FavuoriteMeal favuoriteMeal){
        new taskDelete(mealDAO).execute(favuoriteMeal);
    }

    class taskInsert extends AsyncTask<FavuoriteMeal,Void,Void> {
        private MealDAO dao;
        public taskInsert(MealDAO mealDAO) {
            dao= mealDAO;
        }


        @Override
        protected Void doInBackground(FavuoriteMeal... favuoriteRecipies) {
            dao.insert(favuoriteRecipies[0]);
            return null;
        }
    }

    private class taskDelete extends AsyncTask<FavuoriteMeal,Void,Void> {
        MealDAO mdao;
        public taskDelete(MealDAO mealDAO) {
            mdao= mealDAO;
        }


        @Override
        protected Void doInBackground(FavuoriteMeal... favuoriteRecipies) {
            mdao.deleteData(favuoriteRecipies[0]);
            return null;
        }
    }
}
