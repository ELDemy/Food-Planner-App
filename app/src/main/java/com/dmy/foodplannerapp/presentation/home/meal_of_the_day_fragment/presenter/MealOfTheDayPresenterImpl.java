package com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.presenter.ChangeFavoritePresenterImpl;
import com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.view.MealOfTheDayView;

public class MealOfTheDayPresenterImpl extends ChangeFavoritePresenterImpl implements MealOfTheDayPresenter {
    MealOfTheDayView mealOfTheDayView;

    public MealOfTheDayPresenterImpl(Context context, MealOfTheDayView mealOfTheDayView) {
        super(context, mealOfTheDayView);
        this.mealOfTheDayView = mealOfTheDayView;
    }

    @Override
    public void getMealOfTheDay() {
        mealOfTheDayView.loadMealOfTheDay(true);
        mealsRepo.getMealOfTheDay(
                new MyCallBack<>() {
                    @Override
                    public void onSuccess(MealEntity meal) {
                        mealOfTheDayView.loadMealOfTheDay(false);
                        mealOfTheDayView.showMealOfTheDay(meal);
                    }

                    @Override
                    public void onFailure(Failure failure) {
                        mealOfTheDayView.loadMealOfTheDay(false);
                        mealOfTheDayView.errorMealOfTheDay(failure.getMessage());
                    }
                }
        );
    }

}
