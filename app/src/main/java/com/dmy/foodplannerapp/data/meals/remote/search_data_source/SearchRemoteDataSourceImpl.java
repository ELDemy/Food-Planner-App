package com.dmy.foodplannerapp.data.meals.remote.search_data_source;

import android.util.Log;

import androidx.annotation.NonNull;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.remote.SearchService;
import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.network.MealsNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRemoteDataSourceImpl implements SearchDataSource {
    private static final String TAG = "SearchRemoteDataSourceI";
    SearchService searchService;

    public SearchRemoteDataSourceImpl() {
        this.searchService = MealsNetwork.getInstance().searchService;
    }

    @Override
    public void getCategoryMeals(String query, MyCallBack<List<SearchedMealResponse>> callBack) {
        Call<ListOfSearchMealResponse> call = searchService.getCategoryMeals(query);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ListOfSearchMealResponse> call, @NonNull Response<ListOfSearchMealResponse> response) {
                ListOfSearchMealResponse listOfSearchMealResponse = response.body();
                if (listOfSearchMealResponse != null) {
                    if (listOfSearchMealResponse.getMeals() != null) {
                        callBack.onSuccess(listOfSearchMealResponse.getMeals());
                    } else {
                        callBack.onFailure(new Failure("No Meals for today"));
                    }
                } else {
                    callBack.onFailure(FailureHandler.handle(response, "MealsRemoteDataSourceImpl getCategories"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListOfSearchMealResponse> call, @NonNull Throwable t) {
                callBack.onFailure(FailureHandler.handle(t, "MealsRemoteDataSourceImpl getCategories"));
            }
        });
    }

    @Override
    public void getCountryMeals(String query, MyCallBack<List<SearchedMealResponse>> callBack) {
        Call<ListOfSearchMealResponse> call = searchService.getCountryMeals(query);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ListOfSearchMealResponse> call, @NonNull Response<ListOfSearchMealResponse> response) {
                ListOfSearchMealResponse listOfSearchMealResponse = response.body();
                Log.i("TAG", "onResponse: " + listOfSearchMealResponse);
                if (listOfSearchMealResponse != null) {
                    if (listOfSearchMealResponse.getMeals() != null) {
                        callBack.onSuccess(listOfSearchMealResponse.getMeals());
                    } else {
                        callBack.onFailure(new Failure("No Meals for today"));
                    }
                } else {
                    callBack.onFailure(FailureHandler.handle(response, "MealsRemoteDataSourceImpl getCategories"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListOfSearchMealResponse> call, @NonNull Throwable t) {
                callBack.onFailure(FailureHandler.handle(t, "MealsRemoteDataSourceImpl getCategories"));
            }
        });
    }

    @Override
    public void getIngredientMeals(String query, MyCallBack<List<SearchedMealResponse>> callBack) {
        Call<ListOfSearchMealResponse> call = searchService.getIngredientMeals(query);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ListOfSearchMealResponse> call, @NonNull Response<ListOfSearchMealResponse> response) {
                ListOfSearchMealResponse listOfSearchMealResponse = response.body();
                Log.i("TAG", "onResponse: " + listOfSearchMealResponse);
                if (listOfSearchMealResponse != null) {
                    if (listOfSearchMealResponse.getMeals() != null) {
                        callBack.onSuccess(listOfSearchMealResponse.getMeals());
                    } else {
                        callBack.onFailure(new Failure("No Meals for today"));
                    }
                } else {
                    callBack.onFailure(FailureHandler.handle(response, "MealsRemoteDataSourceImpl getCategories"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListOfSearchMealResponse> call, @NonNull Throwable t) {
                callBack.onFailure(FailureHandler.handle(t, "MealsRemoteDataSourceImpl getCategories"));
            }
        });
    }
}
