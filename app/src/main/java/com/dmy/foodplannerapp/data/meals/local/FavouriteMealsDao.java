package com.dmy.foodplannerapp.data.meals.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;


@Dao
public interface FavouriteMealsDao {
    @Query("SELECT * FROM meals")
    List<MealEntity> getAll();

    @Query("SELECT * FROM meals WHERE id = :id")
    MealEntity getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFavourite(MealEntity meal);

    @Insert
    void insertAll(MealEntity... meals);

    @Delete
    void delete(MealEntity meals);
}
