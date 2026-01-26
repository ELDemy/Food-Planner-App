package com.dmy.foodplannerapp.data.meals.remote.search_data_source;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.SearchedMealResponse;

import java.util.List;

public interface SearchDataSource {
    void getCategoryMeals(String query, MyCallBack<List<SearchedMealResponse>> callBack);

    void getCountryMeals(String query, MyCallBack<List<SearchedMealResponse>> callBack);

    void getIngredientMeals(String query, MyCallBack<List<SearchedMealResponse>> callBack);
}
