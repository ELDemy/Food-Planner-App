package com.dmy.foodplannerapp.data.meals.local.data_source;

import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {
    Single<MealEntity> getMealOfTheDay();

    Completable addMealOfTheDay(MealEntity meal);

    Completable addToFavourite(MealEntity meal);

    Completable removeFromFavourite(MealEntity meal);

    Single<Boolean> isFavourite(MealEntity meal);

    Flowable<List<MealEntity>> getFavouriteMeals();

    Completable clearAllFavorites();

    Flowable<List<MealPlanWithDetails>> getMealsPlansByDate(Date date);

    Flowable<List<Date>> getPlansDatesWithMeals(Date startDate, Date endDate);

    Single<List<MealPlan>> getMealsPlans();

    Completable addMealPlan(MealPlan mealPlan);

    Completable removeMealPlan(MealPlan mealPlan);

    Completable removeMealPlanById(int id);

    Completable clearAllPlans();
}
