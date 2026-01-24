package com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.view;

import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.view.ChangeFavoriteView;

public interface MealOfTheDayView extends ChangeFavoriteView {
    void loadMealOfTheDay(boolean isLoading);

    void showMealOfTheDay(MealEntity meal);

    void errorMealOfTheDay(String message);
}
