package com.dmy.foodplannerapp.data.meals.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.dto.MealDto;
import com.dmy.foodplannerapp.data.model.dto.MealsResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.mapper.MealMapper;
import com.dmy.foodplannerapp.data.network.MealsNetwork;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
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
                    MealDto mealDto = mealsResponse.getMeal();
                    if (mealDto != null) {
                        callBack.onSuccess(MealMapper.toEntity(mealDto));
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
                    MealDto mealDto = mealsResponse.getMeal();
                    if (mealDto != null) {
                        callBack.onSuccess(MealMapper.toEntity(mealDto));
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
                            if (failures.isEmpty()) {
                                callBack.onFailure(new Failure("No Meals for today"));
                                return;
                            }
                            if (failures.get(0) != null) {
                                callBack.onFailure(failures.get(0));
                            }
                        }
                    }

                }
            });
        }


    }

    @Override
    public Single<CategoriesResponse> getCategories() {
        return mealsService.getCategories();
    }

    @Override
    public Single<IngredientsResponse> getIngredients() {
        return mealsService.getIngredients();
    }

    @Override
    public Single<CountriesResponse> getCountries() {
        return mealsService.getCountries();
    }
}
