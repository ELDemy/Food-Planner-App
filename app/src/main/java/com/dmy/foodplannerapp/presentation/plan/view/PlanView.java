package com.dmy.foodplannerapp.presentation.plan.view;

import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.util.Date;
import java.util.List;

public interface PlanView {
    void onMealsLoaded(List<MealPlanWithDetails> meals);

    void onDatesWithMealsLoaded(List<Date> dates);

    void onMealRemoved();

    void onError(String message);
}
