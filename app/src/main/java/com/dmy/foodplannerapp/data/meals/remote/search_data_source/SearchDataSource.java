package com.dmy.foodplannerapp.data.meals.remote.search_data_source;

import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SearchDataSource {
    Single<List<SearchedMealResponse>> getCategoryMeals(String query);

    Single<List<SearchedMealResponse>> getCountryMeals(String query);

    Single<List<SearchedMealResponse>> getIngredientMeals(String query);
}
