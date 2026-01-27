package com.dmy.foodplannerapp.data.meals.remote;

import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
    @GET("filter.php")
    Call<ListOfSearchMealResponse> getCategoryMeals(@Query("c") String name);

    @GET("filter.php")
    Call<ListOfSearchMealResponse> getIngredientMeals(@Query("i") String name);

    @GET("filter.php")
    Call<ListOfSearchMealResponse> getCountryMeals(@Query("a") String name);

}
