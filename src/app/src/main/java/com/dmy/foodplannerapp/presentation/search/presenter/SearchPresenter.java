package com.dmy.foodplannerapp.presentation.search.presenter;

import com.dmy.foodplannerapp.data.model.entity.SearchModel;

import java.util.List;

public interface SearchPresenter {
    void searchMeals(List<SearchModel> arguments);

    void searchMeals(String query, List<SearchModel> arguments);

}
