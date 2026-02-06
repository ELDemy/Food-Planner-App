package com.dmy.foodplannerapp.presentation.favourite.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.view.ChangeFavoriteView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ChangeFavoritePresenterImpl implements ChangeFavoritePresenter {
    public ChangeFavoriteView view;
    public MealsRepo mealsRepo;
    protected final CompositeDisposable disposables = new CompositeDisposable();

    public ChangeFavoritePresenterImpl(Context context, ChangeFavoriteView changeFavoriteView) {
        this.mealsRepo = new MealsRepoImpl(context);
        view = changeFavoriteView;
    }

    public void changeFavourite(MealEntity meal) {
        if (meal.isFavourite()) {
            disposables.add(
                    mealsRepo.removeFromFavourite(meal)
                            .subscribe(
                                    () -> view.changeFavoriteState(false),
                                    error -> view.changeFavoriteState(meal.isFavourite())));
        } else {
            disposables.add(
                    mealsRepo.addToFavourite(meal)
                            .subscribe(
                                    () -> view.changeFavoriteState(true),
                                    error -> view.changeFavoriteState(meal.isFavourite())));
        }
    }

    public void dispose() {
        disposables.clear();
    }
}
