package com.dmy.foodplannerapp.data.network;

import com.dmy.foodplannerapp.data.meals.remote.MealsService;
import com.dmy.foodplannerapp.data.meals.remote.SearchService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealsNetwork {
    private static MealsNetwork instance;
    public MealsService mealsService;
    public SearchService searchService;

    private MealsNetwork() {
        String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mealsService = retrofit.create(MealsService.class);
        searchService = retrofit.create(SearchService.class);
    }

    public static MealsNetwork getInstance() {
        if (instance == null) {
            instance = new MealsNetwork();
        }

        return instance;
    }
}