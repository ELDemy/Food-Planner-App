package com.dmy.foodplannerapp.presentation.favourite.presenter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.view.FavoritesView;

import java.util.List;

public class FavoritePresenterImpl implements FavoritePresenter {
    MealsRepo mealsRepo;
    FavoritesView view;

    public FavoritePresenterImpl(Context context, FavoritesView view) {
        mealsRepo = new MealsRepoImpl(context);
        this.view = view;
    }

    @Override
    public void loadFavourites() {
        view.onLoadStart();
        mealsRepo.getFavouriteMeals(new MyCallBack<>() {
            @Override
            public void onSuccess(LiveData<List<MealEntity>> data) {
                Log.i("loadFavourites", "getFavouriteMeals in presenter: " + data);
                view.onLoadSuccess(data);
            }

            @Override
            public void onFailure(Failure failure) {

            }
        });
    }

    @Override
    public void removeFromFavorites(MealEntity meal) {
        mealsRepo.removeFromFavourite(meal, new MyCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {

            }

            @Override
            public void onFailure(Failure failure) {

            }
        });
    }
}
