package com.dmy.foodplannerapp.presentation.home.presenter;

import com.dmy.foodplannerapp.data.model.MealEntity;

public interface HomeView {
    void loadMealOfTheDay(boolean isLoading);

    void showMealOfTheDay(MealEntity meal);

    void errorMealOfTheDay(String message);

}
