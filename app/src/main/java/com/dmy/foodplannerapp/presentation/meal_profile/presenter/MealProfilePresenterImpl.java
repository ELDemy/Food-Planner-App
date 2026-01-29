package com.dmy.foodplannerapp.presentation.meal_profile.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.meals.repo.meals_plan_repo.MealsPlanRepo;
import com.dmy.foodplannerapp.data.meals.repo.meals_plan_repo.MealsPlanRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.presentation.favourite.presenter.ChangeFavoritePresenterImpl;
import com.dmy.foodplannerapp.presentation.meal_profile.view.MealProfileView;

import java.util.Date;

public class MealProfilePresenterImpl extends ChangeFavoritePresenterImpl implements MealProfilePresenter {

    private final MealsRepo mealsRepo;
    private final MealsPlanRepo mealsPlanRepo;
    private final MealProfileView mealProfileView;

    public MealProfilePresenterImpl(Context context, MealProfileView mealProfileView) {
        super(context, mealProfileView);
        this.mealProfileView = mealProfileView;
        this.mealsRepo = new MealsRepoImpl(context);
        this.mealsPlanRepo = new MealsPlanRepoImpl(context);
    }

    @Override
    public void loadMeal(String mealId) {
        mealProfileView.onLoadingStarted();

        mealsRepo.getMealById(mealId)
                .subscribe(
                        mealProfileView::onMealLoaded,
                        error -> mealProfileView.onLoadingError(error.getMessage()));
    }

    @Override
    public void addMealToPlan(String mealId, Date date, MealPlan.MealType mealType) {
        try {
            MealPlan mealPlan = new MealPlan(date, mealId, mealType);
            mealsPlanRepo.addMealPlan(mealPlan);
            mealProfileView.onMealAddedToPlan();
        } catch (Exception e) {
            mealProfileView.onAddToPlanError(e.getMessage());
        }
    }
}
