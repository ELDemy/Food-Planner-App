package com.dmy.foodplannerapp.data.meals.remote;

import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.dto.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {
    @GET("lookup.php")
    Call<MealsResponse> getMealById(@Query("i") int id);

    @GET("random.php")
    Call<MealsResponse> getRandomMeal();

    @GET("categories.php")
    Single<CategoriesResponse> getCategories();

    @GET("list.php?i=list")
    Single<IngredientsResponse> getIngredients();

    @GET("list.php?a=list")
    Single<CountriesResponse> getCountries();

    @GET("filter.php")
    Single<MealsResponse> getCategoryMeals(@Query("c") String category);

    @GET("filter.php")
    Single<MealsResponse> getCountryMeals(@Query("a") String country);

    @GET("filter.php")
    Single<MealsResponse> getIngredientMeals(@Query("i") String ingredient);

}
