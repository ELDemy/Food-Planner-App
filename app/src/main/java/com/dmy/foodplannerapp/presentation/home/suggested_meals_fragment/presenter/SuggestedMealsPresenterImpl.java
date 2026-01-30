package com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.view.SuggestedMealsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class SuggestedMealsPresenterImpl implements SuggestedMealsPresenter {
    private static final String TAG = "SuggestedMealsPresenter";
    SuggestedMealsView suggestedMealsView;
    MealsRepo mealsRepo;

    public SuggestedMealsPresenterImpl(Context context, SuggestedMealsView view) {
        suggestedMealsView = view;
        mealsRepo = new MealsRepoImpl(context);
    }

    @Override
    public void getSuggestedMeals() {
        suggestedMealsView.onLoad(true);
        mealsRepo.getRandomMeals(10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            if (data.isEmpty() || data == null) {
                                suggestedMealsView.onFailure("No meals found");
                                return;
                            }
                            suggestedMealsView.onLoad(false);
                            suggestedMealsView.updateSuggestedMeals(data);
                        },
                        error -> {
                            suggestedMealsView.onLoad(false);
                            suggestedMealsView.onFailure(FailureHandler.handle(error, TAG).getMessage());
                        }
                );

    }
}
