package com.dmy.foodplannerapp.presentation.meal_profile.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.presentation.favourite.presenter.ChangeFavoritePresenterImpl;
import com.dmy.foodplannerapp.presentation.meal_profile.view.MealProfileView;

public class MealProfilePresenterImpl extends ChangeFavoritePresenterImpl implements MealProfilePresenter {

    public MealProfilePresenterImpl(Context context, MealProfileView mealProfileView) {
        super(context, mealProfileView);
    }
}
