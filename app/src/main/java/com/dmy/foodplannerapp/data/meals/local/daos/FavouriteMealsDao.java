package com.dmy.foodplannerapp.data.meals.local.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dmy.foodplannerapp.data.model.FavoriteMeal;
import com.dmy.foodplannerapp.data.model.FavoriteMealWithDetails;

import java.util.List;


@Dao
public interface FavouriteMealsDao {
    @Transaction
    @Query("SELECT * FROM favorite_meals")
    LiveData<List<FavoriteMealWithDetails>> getAll();

    @Transaction
    @Query("SELECT * FROM favorite_meals WHERE mealId = :id")
    FavoriteMealWithDetails getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFavourite(FavoriteMeal meal);

    @Query("DELETE FROM favorite_meals WHERE mealId = :id")
    void removeFromFavourite(String id);

}
