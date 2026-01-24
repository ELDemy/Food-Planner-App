package com.dmy.foodplannerapp.data.meals.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dmy.foodplannerapp.data.model.MealOfTheDay;

@Dao
public interface MealOfTheDayDao {
    @Query("SELECT * FROM mealoftheday")
    MealOfTheDay get();

    @Insert
    void insert(MealOfTheDay meal);

    @Query("Delete FROM mealoftheday")
    void delete();
}
