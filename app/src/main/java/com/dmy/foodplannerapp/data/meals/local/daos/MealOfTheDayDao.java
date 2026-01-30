package com.dmy.foodplannerapp.data.meals.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dmy.foodplannerapp.data.model.entity.MealOfTheDay;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface MealOfTheDayDao {
    @Query("SELECT * FROM mealoftheday")
    Maybe<MealOfTheDay> get();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(MealOfTheDay meal);

    @Query("DELETE FROM mealoftheday")
    Completable delete();
}
