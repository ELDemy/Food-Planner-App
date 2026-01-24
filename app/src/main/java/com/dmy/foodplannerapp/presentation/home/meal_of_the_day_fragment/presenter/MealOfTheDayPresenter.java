package com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.presenter;

import com.dmy.foodplannerapp.presentation.favourite.presenter.ChangeFavoritePresenter;

public interface MealOfTheDayPresenter extends ChangeFavoritePresenter {
    void getMealOfTheDay();
}
