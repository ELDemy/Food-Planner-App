package com.dmy.foodplannerapp.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.dmy.foodplannerapp.data.meals.local.daos.FavouriteMealsDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealOfTheDayDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsPlanDao;
import com.dmy.foodplannerapp.data.model.Converters;
import com.dmy.foodplannerapp.data.model.FavoriteMeal;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.data.model.MealOfTheDay;
import com.dmy.foodplannerapp.data.model.MealPlan;

@Database(
        entities = {MealEntity.class, FavoriteMeal.class, MealOfTheDay.class, MealPlan.class},
        version = 3
)
@TypeConverters({Converters.class})
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

    public abstract FavouriteMealsDao favMealsDao();

    public abstract MealOfTheDayDao mealOfTheDayDao();

    public abstract MealsPlanDao mealPlanDao();

    public abstract MealsDao mealsDao();
}

