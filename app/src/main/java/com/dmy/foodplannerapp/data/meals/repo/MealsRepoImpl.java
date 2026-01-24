package com.dmy.foodplannerapp.data.meals.repo;

import android.content.Context;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.local.MealsLocalDataSource;
import com.dmy.foodplannerapp.data.meals.local.MealsLocalDataSourceImpl;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSource;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public class MealsRepoImpl implements MealsRepo {
    MealsRemoteDataSource mealsRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;

    public MealsRepoImpl(Context context) {
        mealsRemoteDataSource = new MealsRemoteDataSourceImpl();
        mealsLocalDataSource = new MealsLocalDataSourceImpl(context);
    }

    @Override
    public void getMealById(int id, MyCallBack<MealEntity> callBack) {
        mealsRemoteDataSource.getMealById(id, callBack);
    }

    @Override
    public void getMealOfTheDay(MyCallBack<MealEntity> callBack) {
        mealsRemoteDataSource.getRandomMeal(new MyCallBack<MealEntity>() {
            @Override
            public void onSuccess(MealEntity meal) {
                mealsLocalDataSource.isFavourite(meal, new MyCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isFavourite) {
                        meal.setFavourite(isFavourite);
                    }

                    @Override
                    public void onFailure(Failure failure) {
                    }
                });
                callBack.onSuccess(meal);

            }

            @Override
            public void onFailure(Failure failure) {
                callBack.onFailure(failure);
            }
        });
    }

    @Override
    public void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack) {
        mealsRemoteDataSource.getRandomMeals(quantity, callBack);
    }

    @Override
    public void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        mealsLocalDataSource.addToFavourite(meal, callBack);
    }

    @Override
    public void getFavouriteMeals(MyCallBack<List<MealEntity>> callBack) {

    }

    @Override
    public void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        mealsLocalDataSource.removeFromFavourite(meal, callBack);
    }


}
