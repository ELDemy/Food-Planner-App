package com.dmy.foodplannerapp.data.meals.repo;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSource;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public class MealsRepoImpl implements MealsRepo {
    MealsRemoteDataSource mealsRemoteDataSource;

    public MealsRepoImpl() {
        mealsRemoteDataSource = new MealsRemoteDataSourceImpl();
    }

    @Override
    public void getMealById(int id, MyCallBack<MealEntity> callBack) {
        mealsRemoteDataSource.getMealById(id, callBack);
    }

    @Override
    public void getMealOfTheDay(MyCallBack<MealEntity> callBack) {
        mealsRemoteDataSource.getRandomMeal(callBack);
    }

    @Override
    public void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack) {
        mealsRemoteDataSource.getRandomMeals(quantity, callBack);
    }

    @Override
    public void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {

    }

    @Override
    public void getFavouriteMeals(MyCallBack<List<MealEntity>> callBack) {

    }


}
