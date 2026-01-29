package com.dmy.foodplannerapp.presentation.meal_profile.presenter;

import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.presentation.favourite.presenter.ChangeFavoritePresenter;

import java.util.Date;

public interface MealProfilePresenter extends ChangeFavoritePresenter {
    void addMealToPlan(String mealId, Date date, MealPlan.MealType mealType);
}
