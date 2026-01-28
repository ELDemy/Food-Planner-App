package com.dmy.foodplannerapp.data.meals.repo.meals_plan_repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.meals.local.data_source.meals_plan.MealsPlanLocalDataSource;
import com.dmy.foodplannerapp.data.meals.local.data_source.meals_plan.MealsPlanLocalDataSourceImpl;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.util.Date;
import java.util.List;

public class MealsPlanRepoImpl implements MealsPlanRepo {

    MealsPlanLocalDataSource mealsPlanLocalDataSource;

    public MealsPlanRepoImpl(Context context) {
        mealsPlanLocalDataSource = new MealsPlanLocalDataSourceImpl(context);
    }

    @Override
    public void getMealsByDate(Date date, MyCallBack<LiveData<List<MealPlanWithDetails>>> callBack) {
        mealsPlanLocalDataSource.getMealsByDate(date, callBack);
    }

    @Override
    public void getDatesWithMeals(Date startDate, Date endDate, MyCallBack<LiveData<List<Date>>> callBack) {
        mealsPlanLocalDataSource.getDatesWithMeals(startDate, endDate, callBack);
    }

    @Override
    public void addMealPlan(MealPlan mealPlan) {
        mealsPlanLocalDataSource.addMealPlan(mealPlan);
    }

    @Override
    public void removeMealPlan(MealPlan mealPlan) {
        mealsPlanLocalDataSource.removeMealPlan(mealPlan);
    }

    @Override
    public void removeMealPlanById(int id) {
        mealsPlanLocalDataSource.removeMealPlanById(id);
    }
}
