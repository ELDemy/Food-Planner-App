package com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.view;

import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public interface SuggestedMealsView {
    void updateSuggestedMeals(List<MealEntity> meals);

    void onFailure(String message);
}
