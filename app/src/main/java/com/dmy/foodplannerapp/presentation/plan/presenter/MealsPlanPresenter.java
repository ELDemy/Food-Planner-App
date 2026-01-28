package com.dmy.foodplannerapp.presentation.plan.presenter;

import com.dmy.foodplannerapp.data.model.entity.MealPlan;

import java.util.Date;

public interface MealsPlanPresenter {
    void getMealsByDate(Date date);

    void getDatesWithMeals(Date startDate, Date endDate);

    void addMealPlan(MealPlan mealPlan);

    void removeMealPlan(MealPlan mealPlan);

    void removeMealPlanById(int id);
}
