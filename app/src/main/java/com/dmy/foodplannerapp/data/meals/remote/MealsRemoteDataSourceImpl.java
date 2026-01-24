package com.dmy.foodplannerapp.data.meals.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.data.model.MealsResponse;
import com.dmy.foodplannerapp.data.network.MealsNetwork;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {
    MealsService mealsService;

    public MealsRemoteDataSourceImpl() {
        mealsService = MealsNetwork.getInstance().mealsService;
    }

    @Override
    public void getMealById(int id, MyCallBack<MealEntity> callBack) {
        Call<MealsResponse> call = mealsService.getMealById(id);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                MealsResponse mealsResponse = response.body();
                if (mealsResponse != null) {
                    if (mealsResponse.getMeal() != null) {
                        callBack.onSuccess(mealsResponse.getMeal());
                    } else {
                        callBack.onFailure(new Failure("No Meal found With this Id"));
                    }
                } else {
                    callBack.onFailure(FailureHandler.handle(response, "MealsRemoteDataSourceImpl getMealById"));
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                callBack.onFailure(FailureHandler.handle(t, "MealsRemoteDataSourceImpl getMealById"));
            }
        });

    }


    @Override
    public void getRandomMeal(MyCallBack<MealEntity> callBack) {
        Call<MealsResponse> call = mealsService.getRandomMeal();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                MealsResponse mealsResponse = response.body();
                if (mealsResponse != null) {
                    if (mealsResponse.getMeal() != null) {
                        callBack.onSuccess(mealsResponse.getMeal());
                    } else {
                        callBack.onFailure(new Failure("No Random Meals for today"));
                    }
                } else {
                    callBack.onFailure(FailureHandler.handle(response, "MealsRemoteDataSourceImpl getMealById"));
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                callBack.onFailure(FailureHandler.handle(t, "MealsRemoteDataSourceImpl getRandomMeal"));
            }
        });
    }

    @Override
    public void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack) {
        ArrayList<MealEntity> mealsList = new ArrayList<>();
        ArrayList<Failure> failures = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            Log.i("TAG", "getRandomMeals: " + i);
            final int finalI = i;
            getRandomMeal(new MyCallBack<>() {
                @Override
                public void onSuccess(MealEntity data) {
                    Log.i("TAG", "getMeal" + finalI + ": " + data);
                    mealsList.add(data);

                    if (finalI == quantity - 1) {
                        callBack.onSuccess(mealsList);
                    }
                }

                @Override
                public void onFailure(Failure failure) {
                    failures.add(failure);
                    if (finalI == quantity - 1) {
                        if (!mealsList.isEmpty()) {
                            callBack.onSuccess(mealsList);
                        } else {
                            callBack.onFailure(failures.getFirst());
                        }
                    }

                }
            });
        }


    }
}
