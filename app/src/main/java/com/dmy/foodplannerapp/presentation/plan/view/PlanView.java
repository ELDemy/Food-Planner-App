package com.dmy.foodplannerapp.presentation.plan.view;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.util.Date;
import java.util.List;

public interface PlanView {
    void onMealsLoaded(LiveData<List<MealPlanWithDetails>> meals);

    void onDatesWithMealsLoaded(LiveData<List<Date>> dates);

    void onMealRemoved();

    void onError(String message);
}
