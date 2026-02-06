package com.dmy.foodplannerapp.data.meals.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dmy.foodplannerapp.data.model.entity.FavoriteMeal;
import com.dmy.foodplannerapp.data.model.entity.FavoriteMealWithDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface FavouriteMealsDao {
    @Transaction
    @Query("SELECT * FROM favorite_meals")
    Flowable<List<FavoriteMealWithDetails>> getAll();

    @Transaction
    @Query("SELECT * FROM favorite_meals WHERE mealId = :id")
    Maybe<FavoriteMealWithDetails> getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToFavourite(FavoriteMeal meal);

    @Query("DELETE FROM favorite_meals WHERE mealId = :id")
    Completable removeFromFavourite(String id);

    @Query("DELETE FROM favorite_meals")
    Completable clearAll();
}
