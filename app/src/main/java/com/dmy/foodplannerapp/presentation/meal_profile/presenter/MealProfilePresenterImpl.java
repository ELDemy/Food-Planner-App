package com.dmy.foodplannerapp.presentation.meal_profile.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.presentation.favourite.presenter.ChangeFavoritePresenterImpl;
import com.dmy.foodplannerapp.presentation.meal_profile.view.MealProfileView;

public class MealProfilePresenterImpl extends ChangeFavoritePresenterImpl implements MealProfilePresenter {

    private final MealsRepo mealsRepo;
    private final MealProfileView mealProfileView;

    public MealProfilePresenterImpl(Context context, MealProfileView mealProfileView) {
        super(context, mealProfileView);
        this.mealProfileView = mealProfileView;
        this.mealsRepo = new MealsRepoImpl(context);
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
    public void addMealToPlan(MealPlan mealPlan) {
        try {
            mealsRepo.addMealPlan(mealPlan);
            mealProfileView.onMealAddedToPlan();
        } catch (Exception e) {
            mealProfileView.onAddToPlanError(e.getMessage());
        }
    }
}
