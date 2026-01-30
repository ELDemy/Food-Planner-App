package com.dmy.foodplannerapp.data.meals.local.data_source;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {
    void getMealOfTheDay(MyCallBack<MealEntity> callBack);

    void addMealOfTheDay(MealEntity meal);

    void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void isFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void getFavouriteMeals(MyCallBack<LiveData<List<MealEntity>>> callBack);

    Completable clearAllFavorites();

    Completable clearAllPlans();


    void getMealsPlansByDate(Date date, MyCallBack<LiveData<List<MealPlanWithDetails>>> callBack);

    void getPlansDatesWithMeals(Date startDate, Date endDate, MyCallBack<LiveData<List<Date>>> callBack);

    Single<List<MealPlan>> getMealsPlans();

    void addMealPlan(MealPlan mealPlan);

    void removeMealPlan(MealPlan mealPlan);

    void removeMealPlanById(int id);

}
