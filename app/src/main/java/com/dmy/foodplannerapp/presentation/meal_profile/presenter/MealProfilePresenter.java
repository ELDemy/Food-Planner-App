package com.dmy.foodplannerapp.presentation.meal_profile.presenter;

import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.presentation.favourite.presenter.ChangeFavoritePresenter;

public interface MealProfilePresenter extends ChangeFavoritePresenter {
    void loadMeal(String mealId);

    void addMealToPlan(MealPlan mealPlan);
}
