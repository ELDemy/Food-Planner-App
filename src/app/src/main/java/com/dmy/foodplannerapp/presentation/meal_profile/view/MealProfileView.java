package com.dmy.foodplannerapp.presentation.meal_profile.view;

import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.view.ChangeFavoriteView;

public interface MealProfileView extends ChangeFavoriteView {
    void onMealLoaded(MealEntity meal);

    void onLoadingStarted();

    void onLoadingError(String message);

    void onMealAddedToPlan();

    void onAddToPlanError(String message);
}
