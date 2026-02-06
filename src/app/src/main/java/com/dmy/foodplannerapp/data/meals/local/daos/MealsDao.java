package com.dmy.foodplannerapp.data.meals.local.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface MealsDao {
    @Query("SELECT * FROM meals")
    Flowable<List<MealEntity>> getAll();

    @Query("SELECT * FROM meals WHERE idMeal = :id")
    Maybe<MealEntity> getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(MealEntity meal);

    @Delete
    Completable delete(MealEntity meal);
}
