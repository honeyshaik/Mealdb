package com.example.dell.mealdb.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavuoriteMeal.class},version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase{
    public abstract MealDAO mealDAO();
    private static MyDatabase INSTANCE;
    static MyDatabase getDatabase(final Context context){

        if(INSTANCE==null){
            synchronized (MyDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class,"favuorite.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
