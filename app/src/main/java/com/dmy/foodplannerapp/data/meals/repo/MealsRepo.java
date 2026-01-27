package com.dmy.foodplannerapp.data.meals.repo;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.List;

public interface MealsRepo {
    void getMealById(int id, MyCallBack<MealEntity> callBack);

    void getMealOfTheDay(MyCallBack<MealEntity> callBack);

    void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack);

    void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void getFavouriteMeals(MyCallBack<LiveData<List<MealEntity>>> callBack);

    void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack);
}
