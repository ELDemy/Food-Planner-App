package com.dmy.foodplannerapp.presentation.favourite.view;

import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.List;

public interface FavoritesScreenView extends ChangeFavoriteView {
    void onLoadSuccess(List<MealEntity> meals);
}
