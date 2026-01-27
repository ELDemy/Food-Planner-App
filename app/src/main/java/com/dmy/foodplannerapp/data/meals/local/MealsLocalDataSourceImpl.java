package com.dmy.foodplannerapp.data.meals.local;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.db.AppDatabase;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.local.daos.FavouriteMealsDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealOfTheDayDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsDao;
import com.dmy.foodplannerapp.data.model.FavoriteMeal;
import com.dmy.foodplannerapp.data.model.FavoriteMealWithDetails;
import com.dmy.foodplannerapp.data.model.MealOfTheDay;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    FavouriteMealsDao favouriteMealsDao;
    MealOfTheDayDao mealOfTheDayDao;
    MealsDao mealsDao;
    Handler mainHandler = new Handler(Looper.getMainLooper());

    public MealsLocalDataSourceImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        favouriteMealsDao = db.favMealsDao();
        mealOfTheDayDao = db.mealOfTheDayDao();
        mealsDao = db.mealsDao();
    }


    @Override
    public void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        FavoriteMeal mealFavorite = new FavoriteMeal(meal.getId());
        new Thread(() -> {
            try {
                meal.setFavourite(true);
                mealsDao.insert(meal);
                favouriteMealsDao.addToFavourite(mealFavorite);
                mainHandler.post(() -> callBack.onSuccess(true));
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "addToFavourite")));
            }
        }).start();
    }

    @Override
    public void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        new Thread(() -> {
            try {
                meal.setFavourite(false);
                favouriteMealsDao.removeFromFavourite(meal.getId());
                mainHandler.post(() -> callBack.onSuccess(false));
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "addToFavourite")));
            }
        }).start();
    }


    @Override
    public void isFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        Thread th = new Thread(() -> {
            try {
                FavoriteMealWithDetails favouriteMeal = favouriteMealsDao.getById(meal.getId());
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

    private List<MealEntity> mapToMeals(List<FavoriteMealWithDetails> list) {
        List<MealEntity> meals = new ArrayList<>();
        for (FavoriteMealWithDetails item : list) {
            item.meal.setFavourite(true);
            meals.add(item.meal);
        }
        return meals;
    }

    @Override
    public void getFavouriteMeals(MyCallBack<LiveData<List<MealEntity>>> callBack) {
        try {
            LiveData<List<FavoriteMealWithDetails>> favMeals = favouriteMealsDao.getAll();
            LiveData<List<MealEntity>> meals = Transformations.map(favMeals, this::mapToMeals);
            callBack.onSuccess(meals);

        } catch (Exception e) {
            callBack.onFailure(FailureHandler.handle(e, "getFavouriteMeals"));
        }

    }

    @Override
    public void getMealOfTheDay(MyCallBack<MealEntity> callBack) {
        Thread th = new Thread(() -> {
            try {
                MealOfTheDay mealOfTheDay = mealOfTheDayDao.get();
                Log.i("TAG", "isFavourite: " + mealOfTheDay);
                if (mealOfTheDay != null) {
                    if (mealOfTheDay.day == LocalDate.now().getDayOfMonth()) {
                        mainHandler.post(() -> callBack.onSuccess(mealOfTheDay.meal));
                    } else {
                        mealOfTheDayDao.delete();
                        mainHandler.post(() -> callBack.onFailure(null));
                    }
                } else {
                    mainHandler.post(() -> callBack.onFailure(null));
                }
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getMealOfTheDay")));
            }
        });
        th.start();
    }

    @Override
    public void addMealOfTheDay(MealEntity meal) {
        Thread th = new Thread(() -> {
            try {
                MealOfTheDay mealOfTheDay = new MealOfTheDay();
                mealOfTheDay.setMeal(meal);
                mealOfTheDay.setDay(LocalDate.now().getDayOfMonth());

                mealOfTheDayDao.insert(mealOfTheDay);
            } catch (Exception e) {
                FailureHandler.handle(e, "addMealOfTheDay");
            }
        });
        th.start();
    }
}
