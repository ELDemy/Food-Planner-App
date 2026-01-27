package com.dmy.foodplannerapp.data.meals.local.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.List;

@Dao
public interface MealsDao {
    @Query("SELECT * FROM meals")
    LiveData<List<MealEntity>> getAll();

    @Query("SELECT * FROM meals WHERE idMeal = :id")
    MealEntity getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealEntity meal);

    @Delete
    void delete(MealEntity meal);
}
