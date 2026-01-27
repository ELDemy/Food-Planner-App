package com.dmy.foodplannerapp.data.meals.local.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dmy.foodplannerapp.data.model.MealPlan;

@Dao
public interface MealsPlanDao {

    @Query("SELECT * FROM mealsPlan")
    MealPlan get();

    @Insert
    void insert(MealPlan mealPlan);

    @Delete
    void delete(MealPlan mealPlan);

}
