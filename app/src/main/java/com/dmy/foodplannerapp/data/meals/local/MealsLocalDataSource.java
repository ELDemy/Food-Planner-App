package com.dmy.foodplannerapp.data.meals.local;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public interface MealsLocalDataSource {

    void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void isFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void getFavouriteMeals(MyCallBack<List<MealEntity>> callBack);


}
