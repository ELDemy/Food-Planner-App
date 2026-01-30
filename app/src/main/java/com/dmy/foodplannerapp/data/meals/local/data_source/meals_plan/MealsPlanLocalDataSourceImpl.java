package com.dmy.foodplannerapp.data.meals.local.data_source.meals_plan;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.db.AppDatabase;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsPlanDao;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.util.Date;
import java.util.List;

public class MealsPlanLocalDataSourceImpl implements MealsPlanLocalDataSource {
    private static final String TAG = "MealsPlanLocalDataSource";
    MealsPlanDao mealsPlanDao;
    MealsDao mealsDao;
    Handler mainHandler = new Handler(Looper.getMainLooper());

    public MealsPlanLocalDataSourceImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mealsPlanDao = db.mealPlanDao();
        mealsDao = db.mealsDao();
    }

    @Override
    public void getMealsByDate(Date date, MyCallBack<LiveData<List<MealPlanWithDetails>>> callBack) {
        Thread th = new Thread(() -> {
            try {
                Log.i(TAG, "getMealsByDate: " + date);
                Log.i(TAG, "getMealsByDate: " + date.getTime());

                LiveData<List<MealPlanWithDetails>> mealPlanWithDetails = mealsPlanDao.getMealsByDate(date);
                mainHandler.post(() -> {
                    callBack.onSuccess(mealPlanWithDetails);
                });
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getMealsByDate")));
            }
        });
        th.start();
    }

    @Override
    public void getDatesWithMeals(Date startDate, Date endDate, MyCallBack<LiveData<List<Date>>> callBack) {
        Thread th = new Thread(() -> {
            try {
                LiveData<List<Date>> dates = mealsPlanDao.getDatesWithMeals(startDate, endDate);
                mainHandler.post(() -> callBack.onSuccess(dates));
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getDatesWithMeals")));
            }
        });
        th.start();
    }

    @Override
    public void addMealPlan(MealPlan mealPlan) {
        Thread th = new Thread(() -> {
            try {
                mealsDao.insert(mealPlan.getMeal());
                mealsPlanDao.insert(mealPlan);
            } catch (Exception e) {
                FailureHandler.handle(e, "addMealPlan");
            }
        });
        th.start();
    }

    @Override
    public void removeMealPlan(MealPlan mealPlan) {
        Thread th = new Thread(() -> {
            try {
                mealsPlanDao.delete(mealPlan);
            } catch (Exception e) {
                FailureHandler.handle(e, "removeMealPlan");
            }
        });
        th.start();
    }

    @Override
    public void removeMealPlanById(int id) {
        Thread th = new Thread(() -> {
            try {
                mealsPlanDao.deleteById(id);
            } catch (Exception e) {
                FailureHandler.handle(e, "removeMealPlanById");
            }
        });
        th.start();
    }
}
