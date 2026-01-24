package com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.view;

import com.dmy.foodplannerapp.data.model.MealEntity;

public interface MealOfTheDayView {
    void loadMealOfTheDay(boolean isLoading);

    void showMealOfTheDay(MealEntity meal);

    void errorMealOfTheDay(String message);

    void changeFavouriteState(boolean isFavourite);

}
