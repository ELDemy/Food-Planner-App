package com.dmy.foodplannerapp.data.meals.remote.search_data_source;

import com.dmy.foodplannerapp.data.meals.remote.SearchService;
import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.network.MealsNetwork;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchRemoteDataSourceImpl implements SearchDataSource {
    private static final String TAG = "SearchRemoteDataSourceI";
    SearchService searchService;

    public SearchRemoteDataSourceImpl() {
        this.searchService = MealsNetwork.getInstance().searchService;
    }

    @Override
    public Single<List<SearchedMealResponse>> getCategoryMeals(String query) {
        return searchService.getCategoryMeals(query)
                .subscribeOn(Schedulers.io())
                .map(ListOfSearchMealResponse::getMeals);


    }

    @Override
    public Single<List<SearchedMealResponse>> getCountryMeals(String query) {
        return searchService.getCountryMeals(query)
                .subscribeOn(Schedulers.io())
                .map(ListOfSearchMealResponse::getMeals);

    }

    @Override
    public Single<List<SearchedMealResponse>> getIngredientMeals(String query) {
        return searchService.getIngredientMeals(query)
                .subscribeOn(Schedulers.io())
                .map(ListOfSearchMealResponse::getMeals);
    }
}
