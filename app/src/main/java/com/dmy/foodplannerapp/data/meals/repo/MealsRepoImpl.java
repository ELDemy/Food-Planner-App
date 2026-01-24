package com.dmy.foodplannerapp.data.meals.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

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
        mealsRemoteDataSource.getMealById(id, new MyCallBack<>() {
            @Override
            public void onSuccess(MealEntity meal) {
                checkIfMealIsInFavorite(meal, () -> callBack.onSuccess(meal));
            }

            @Override
            public void onFailure(Failure failure) {
                callBack.onFailure(failure);
            }
        });
    }


    @Override
    public void getMealOfTheDay(MyCallBack<MealEntity> callBack) {
        mealsLocalDataSource.getMealOfTheDay(new MyCallBack<>() {
            @Override
            public void onSuccess(MealEntity meal) {
                checkIfMealIsInFavorite(meal, () -> callBack.onSuccess(meal));
            }

            @Override
            public void onFailure(Failure failure) {
                mealsRemoteDataSource.getRandomMeal(new MyCallBack<>() {
                    @Override
                    public void onSuccess(MealEntity meal) {
                        mealsLocalDataSource.addMealOfTheDay(meal);
                        checkIfMealIsInFavorite(meal, () -> callBack.onSuccess(meal));
                    }

                    @Override
                    public void onFailure(Failure failure) {
                        callBack.onFailure(failure);
                    }
                });
            }
        });
    }

    private void checkIfMealIsInFavorite(MealEntity meal, Runnable onCompleted) {
        mealsLocalDataSource.isFavourite(meal,
                new MyCallBack<>() {
                    @Override
                    public void onSuccess(Boolean isFavourite) {
                        Log.i("TAG", "during update favorite" + isFavourite);
                        meal.setFavourite(isFavourite);
                        onCompleted.run();
                    }

                    @Override
                    public void onFailure(Failure failure) {
                        onCompleted.run();
                    }
                });
    }

    @Override
    public void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack) {
        mealsRemoteDataSource.getRandomMeals(quantity, new MyCallBack<>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                for (int i = 0; i < meals.size(); i++) {
                    final int finalI = i;
                    checkIfMealIsInFavorite(meals.get(i),
                            () -> {
                                if (finalI == quantity - 1) {
                                    callBack.onSuccess(meals);
                                }
                            });
                }
            }

            @Override
            public void onFailure(Failure failure) {
                callBack.onFailure(failure);
            }
        });
    }

    @Override
    public void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        mealsLocalDataSource.addToFavourite(meal, callBack);
    }

    @Override
    public void getFavouriteMeals(MyCallBack<LiveData<List<MealEntity>>> callBack) {
        mealsLocalDataSource.getFavouriteMeals(callBack);
    }

    @Override
    public void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        mealsLocalDataSource.removeFromFavourite(meal, callBack);
    }
}
