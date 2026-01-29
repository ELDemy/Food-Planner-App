package com.dmy.foodplannerapp.data.meals.remote;

import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
    @GET("filter.php")
    Single<ListOfSearchMealResponse> getCategoryMeals(@Query("c") String name);

    @GET("filter.php")
    Single<ListOfSearchMealResponse> getIngredientMeals(@Query("i") String name);

    @GET("filter.php")
    Single<ListOfSearchMealResponse> getCountryMeals(@Query("a") String name);

}
