package com.dmy.foodplannerapp.presentation.meal_profile.view;

import com.dmy.foodplannerapp.presentation.favourite.view.ChangeFavoriteView;

public interface MealProfileView extends ChangeFavoriteView {
    void onMealAddedToPlan();

    void onAddToPlanError(String message);
    
}
