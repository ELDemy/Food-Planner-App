package com.dmy.foodplannerapp.data.meals.local;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.List;

public interface MealsLocalDataSource {

    void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void isFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void getFavouriteMeals(MyCallBack<LiveData<List<MealEntity>>> callBack);

    void getMealOfTheDay(MyCallBack<MealEntity> callBack);

    void addMealOfTheDay(MealEntity meal);

}
