package com.dmy.foodplannerapp.presentation.favourite.view;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public interface FavoritesView {
    void onLoadSuccess(LiveData<List<MealEntity>> meals);
}
