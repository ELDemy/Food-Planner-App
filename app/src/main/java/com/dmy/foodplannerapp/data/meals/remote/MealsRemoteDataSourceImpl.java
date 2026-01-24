package com.dmy.foodplannerapp.data.meals.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.dmy.foodplannerapp.data.model.MealsResponse;
import com.dmy.foodplannerapp.data.network.MealsNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {
    MealsService mealsService;

    public MealsRemoteDataSourceImpl() {
        mealsService = MealsNetwork.getInstance().mealsService;
    }

    @Override
    public void getMealById(int id) {

        Call<MealsResponse> call = mealsService.getMealById(id);
        call.enqueue(
                new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<MealsResponse> call, Response<MealsResponse> response) {
                        if (response.body() != null) {
                            Log.i("MealsRemoteDataSourceImpl", response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsResponse> call, Throwable t) {
                        t.printStackTrace();
                        Log.i("MealsRemoteDataSourceImpl", t.getMessage());
                    }
                }
        );

    }

    @Override
    public void getRandomMeal() {

    }
}
