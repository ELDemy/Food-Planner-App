package com.dmy.foodplannerapp.presentation.favourite.presenter;

import android.content.Context;
import android.util.Log;

import com.dmy.foodplannerapp.presentation.favourite.view.FavoritesScreenView;

public class FavoritePresenterImpl extends ChangeFavoritePresenterImpl implements FavoritePresenter {
    private static final String TAG = "FavoritePresenterImpl";
    FavoritesScreenView view;

    public FavoritePresenterImpl(Context context, FavoritesScreenView view) {
        super(context, view);
        this.view = view;
    }

    @Override
    public void loadFavourites() {
        disposables.add(
                mealsRepo.getFavouriteMeals()
                        .subscribe(
                                meals -> {
                                    Log.i(TAG, "getFavouriteMeals: " + meals.size());
                                    view.onLoadSuccess(meals);
                                },
                                error -> Log.e(TAG, "Error loading favourites", error)));
    }
}
