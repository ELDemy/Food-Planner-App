package com.dmy.foodplannerapp.presentation.favourite.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.view.ChangeFavoriteView;

public class ChangeFavoritePresenterImpl implements ChangeFavoritePresenter {
    public ChangeFavoriteView view;
    public MealsRepo mealsRepo;

    public ChangeFavoritePresenterImpl(Context context, ChangeFavoriteView changeFavoriteView) {
        this.mealsRepo = new MealsRepoImpl(context);
        view = changeFavoriteView;
    }

    public void changeFavourite(MealEntity meal) {
        MyCallBack<Boolean> callBack = new MyCallBack<>() {
            @Override
            public void onSuccess(Boolean isFavourite) {
                view.changeFavoriteState(isFavourite);
            }

            @Override
            public void onFailure(Failure failure) {
                view.changeFavoriteState(meal.isFavourite());
            }
        };

        if (meal.isFavourite()) {
            mealsRepo.removeFromFavourite(meal, callBack);
        } else {
            mealsRepo.addToFavourite(meal, callBack);
        }
    }
}
