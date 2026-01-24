package com.dmy.foodplannerapp.data.meals.remote;

import com.dmy.foodplannerapp.data.model.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {
    @GET("lookup.php")
    Call<MealsResponse> getMealById(@Query("i") int id);

    @GET("random.php")
    Call<MealsResponse> getRandomMeal();
}
