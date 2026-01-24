package com.dmy.foodplannerapp.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dmy.foodplannerapp.data.meals.local.FavouriteMealsDao;
import com.dmy.foodplannerapp.data.meals.local.MealOfTheDayDao;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.data.model.MealOfTheDay;

@Database(entities = {MealEntity.class, MealOfTheDay.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private final static String DB_NAME = "meals_db";
    private static AppDatabase db = null;

    public static AppDatabase getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, DB_NAME).build();
        }
        return db;
    }

    public abstract FavouriteMealsDao mealsDao();

    public abstract MealOfTheDayDao mealOfTheDayDao();
}

