package com.dmy.foodplannerapp.data.meals.remote.meals_data_source;

import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;
import com.dmy.foodplannerapp.data.model.dto.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {
    @GET("lookup.php")
    Single<MealsResponse> getMealById(@Query("i") String id);

    @GET("search.php")
    Single<ListOfSearchMealResponse> getMealByName(@Query("s") String query);

    @GET("random.php")
    Single<MealsResponse> getRandomMeal();

    @GET("categories.php")
    Single<CategoriesResponse> getCategories();

    @GET("list.php?i=list")
    Single<IngredientsResponse> getIngredients();

    @GET("list.php?a=list")
    Single<CountriesResponse> getCountries();

    @GET("filter.php")
    Single<ListOfSearchMealResponse> getCategoryMeals(@Query("c") String name);

    @GET("filter.php")
    Single<ListOfSearchMealResponse> getIngredientMeals(@Query("i") String name);

    @GET("filter.php")
    Single<ListOfSearchMealResponse> getCountryMeals(@Query("a") String name);
}
