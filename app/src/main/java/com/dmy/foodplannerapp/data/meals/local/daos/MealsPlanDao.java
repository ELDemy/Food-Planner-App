package com.dmy.foodplannerapp.data.meals.local.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealsPlanDao {
    @Transaction
    @Query("SELECT * FROM mealsPlan WHERE date = :date")
    LiveData<List<MealPlanWithDetails>> getMealsByDate(Date date);

    @Transaction
    @Query("SELECT * FROM mealsPlan")
    LiveData<List<MealPlanWithDetails>> getMeals();

    @Transaction
    @Query("SELECT * FROM mealsPlan WHERE date = :date AND mealType = :mealType")
    LiveData<List<MealPlanWithDetails>> getMealsByDateAndType(Date date, String mealType);

    @Transaction
    @Query("SELECT * FROM mealsPlan")
    Single<List<MealPlanWithDetails>> getMealsPlans();

    @Query("SELECT DISTINCT date FROM mealsPlan WHERE date BETWEEN :startDate AND :endDate")
    LiveData<List<Date>> getDatesWithMeals(Date startDate, Date endDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealPlan mealPlan);

    @Delete
    void delete(MealPlan mealPlan);

    @Query("DELETE FROM mealsPlan")
    Completable clearAll();


    @Query("DELETE FROM mealsPlan WHERE id = :id")
    void deleteById(int id);
}
