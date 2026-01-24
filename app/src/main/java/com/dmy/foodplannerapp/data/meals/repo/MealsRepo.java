package com.dmy.foodplannerapp.data.meals.repo;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public interface MealsRepo {
    void getMealById(int id, MyCallBack<MealEntity> callBack);

    void getMealOfTheDay(MyCallBack<MealEntity> callBack);

    void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack);

    void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void getFavouriteMeals(MyCallBack<List<MealEntity>> callBack);
}
