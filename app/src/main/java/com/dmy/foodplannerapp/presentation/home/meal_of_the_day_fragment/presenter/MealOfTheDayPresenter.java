package com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.presenter;

import com.dmy.foodplannerapp.data.model.MealEntity;

public interface MealOfTheDayPresenter {
    void getMealOfTheDay();

    void addToFavourite(MealEntity meal);
}
