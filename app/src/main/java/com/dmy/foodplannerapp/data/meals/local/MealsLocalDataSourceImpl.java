package com.dmy.foodplannerapp.data.meals.local;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.db.AppDatabase;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    FavouriteMealsDao favouriteMealsDao;
    Handler mainHandler = new Handler(Looper.getMainLooper());

    public MealsLocalDataSourceImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        favouriteMealsDao = db.mealsDao();
    }


    @Override
    public void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        new Thread(() -> {
            try {
                meal.setFavourite(true);
                favouriteMealsDao.addToFavourite(meal);
                mainHandler.post(() -> callBack.onSuccess(true));
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "addToFavourite")));
            }
        }).start();
    }


    @Override
    public void isFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        Thread th = new Thread(() -> {
            try {
                MealEntity favouriteMeal = favouriteMealsDao.getById(meal.getId());
                Log.i("TAG", "isFavourite: " + favouriteMeal);
                if (favouriteMeal != null) {
                    mainHandler.post(() -> callBack.onSuccess(true));
                } else {
                    mainHandler.post(() -> callBack.onSuccess(false));
                }
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getFavouriteMeals")));
            }
        });
        th.start();
    }

    @Override
    public void getFavouriteMeals(MyCallBack<List<MealEntity>> callBack) {
        new Thread(() -> {
            try {
                List<MealEntity> meals = favouriteMealsDao.getAll();
                mainHandler.post(() -> callBack.onSuccess(meals));
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getFavouriteMeals")));
            }
        }).start();
    }
}
