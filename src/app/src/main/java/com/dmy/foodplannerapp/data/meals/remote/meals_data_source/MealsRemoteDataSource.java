package com.dmy.foodplannerapp.data.meals.remote.meals_data_source;

import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;
import com.dmy.foodplannerapp.data.model.dto.MealDto;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    Single<MealEntity> getMealById(String id);

    Single<ListOfSearchMealResponse> getMealsByName(String query);

    Single<MealDto> getRandomMeal();

    Single<List<MealDto>> getRandomMeals(int quantity);

    Single<CategoriesResponse> getCategories();

    Single<IngredientsResponse> getIngredients();

    Single<CountriesResponse> getCountries();

    Single<List<SearchedMealResponse>> getCategoryMeals(String query);

    Single<List<SearchedMealResponse>> getCountryMeals(String query);

    Single<List<SearchedMealResponse>> getIngredientMeals(String query);
}
