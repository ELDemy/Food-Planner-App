package com.dmy.foodplannerapp.presentation.favourite.presenter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.view.FavoritesScreenView;

import java.util.List;

public class FavoritePresenterImpl extends ChangeFavoritePresenterImpl implements FavoritePresenter {
    FavoritesScreenView view;

    public FavoritePresenterImpl(Context context, FavoritesScreenView view) {
        super(context, view);
        this.view = view;
    }

    @Override
    public void loadFavourites() {
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
}
