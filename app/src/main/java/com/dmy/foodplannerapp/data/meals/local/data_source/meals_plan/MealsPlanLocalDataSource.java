package com.dmy.foodplannerapp.data.meals.local.data_source.meals_plan;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.util.Date;
import java.util.List;

public interface MealsPlanLocalDataSource {
    void getMealsByDate(Date date, MyCallBack<LiveData<List<MealPlanWithDetails>>> callBack);

    void getDatesWithMeals(Date startDate, Date endDate, MyCallBack<LiveData<List<Date>>> callBack);

    void addMealPlan(MealPlan mealPlan);

    void removeMealPlan(MealPlan mealPlan);

    void removeMealPlanById(int id);
}
