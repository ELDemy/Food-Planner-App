package com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.presentation.favourite.presenter.ChangeFavoritePresenterImpl;
import com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.view.MealOfTheDayView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MealOfTheDayPresenterImpl extends ChangeFavoritePresenterImpl implements MealOfTheDayPresenter {
    private static final String TAG = "MealOfTheDayPresenter";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MealOfTheDayView mealOfTheDayView;

    public MealOfTheDayPresenterImpl(Context context, MealOfTheDayView mealOfTheDayView) {
        super(context, mealOfTheDayView);
        this.mealOfTheDayView = mealOfTheDayView;
    }

    @Override
    public void getMealOfTheDay() {
        mealOfTheDayView.loadMealOfTheDay(true);
        disposables.add(
                mealsRepo.getMealOfTheDay().subscribe(
                        meal -> {
                            mealOfTheDayView.loadMealOfTheDay(false);
                            mealOfTheDayView.showMealOfTheDay(meal);
                        },
                        error -> {
                            mealOfTheDayView.loadMealOfTheDay(false);
                            mealOfTheDayView.errorMealOfTheDay(FailureHandler.handle(error, TAG).getMessage());
                        }
                )
        );
    }

    public void dispose() {
        disposables.clear();
    }
}
