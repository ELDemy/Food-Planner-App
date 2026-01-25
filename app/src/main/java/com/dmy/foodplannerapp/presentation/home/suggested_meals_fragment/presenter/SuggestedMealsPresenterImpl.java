package com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.view.SuggestedMealsView;

import java.util.List;

public class SuggestedMealsPresenterImpl implements SuggestedMealsPresenter {
    SuggestedMealsView suggestedMealsView;
    MealsRepo mealsRepo;

    public SuggestedMealsPresenterImpl(Context context, SuggestedMealsView view) {
        suggestedMealsView = view;
        mealsRepo = new MealsRepoImpl(context);
    }

    @Override
    public void getSuggestedMeals() {
        suggestedMealsView.onLoad(true);
        mealsRepo.getRandomMeals(10, new MyCallBack<>() {
            @Override
            public void onSuccess(List<MealEntity> data) {
                suggestedMealsView.onLoad(false);
                suggestedMealsView.updateSuggestedMeals(data);
            }

            @Override
            public void onFailure(Failure failure) {
                suggestedMealsView.onLoad(false);
                suggestedMealsView.onFailure(failure.getMessage());
            }
        });
    }
}
