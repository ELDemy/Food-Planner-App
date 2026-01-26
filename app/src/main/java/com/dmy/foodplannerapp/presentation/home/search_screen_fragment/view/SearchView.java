package com.dmy.foodplannerapp.presentation.home.search_screen_fragment.view;

import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public interface SearchView {
    void onLoad();

    void onSuccess(List<MealEntity> data);

    void onFailure(Failure failure);

}
