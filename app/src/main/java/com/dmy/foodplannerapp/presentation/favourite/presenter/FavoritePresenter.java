package com.dmy.foodplannerapp.presentation.favourite.presenter;

import com.dmy.foodplannerapp.data.model.MealEntity;

public interface FavoritePresenter {
    void loadFavourites();

    void removeFromFavorites(MealEntity meal);

}
