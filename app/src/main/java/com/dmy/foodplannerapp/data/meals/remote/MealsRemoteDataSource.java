package com.dmy.foodplannerapp.data.meals.remote;

import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;
import com.dmy.foodplannerapp.data.model.dto.MealDto;
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
}
