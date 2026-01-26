package com.dmy.foodplannerapp.data.meals.repo.search_repo;

import android.util.Log;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSource;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.meals.remote.search_data_source.SearchDataSource;
import com.dmy.foodplannerapp.data.meals.remote.search_data_source.SearchRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.model.ArgumentSearchScreenModel;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.data.model.SearchedMealResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SearchRepoImpl implements SearchRepo {
    private static final String TAG = "SearchRepoImpl";
    SearchDataSource searchDataSource;
    MealsRemoteDataSource mealsRemoteDataSource;

    public SearchRepoImpl() {
        searchDataSource = new SearchRemoteDataSourceImpl();
        mealsRemoteDataSource = new MealsRemoteDataSourceImpl();
    }

    @Override
    public void searchMeals(ArgumentSearchScreenModel arguments, MyCallBack<List<MealEntity>> callBack) {
        ArgumentSearchScreenModel.SearchType searchType = arguments.getType();

        if (searchType == ArgumentSearchScreenModel.SearchType.CATEGORY) {
            searchDataSource.getCategoryMeals(arguments.name, new MyCallBack<List<SearchedMealResponse>>() {
                @Override
                public void onSuccess(List<SearchedMealResponse> data) {
                    Log.i(TAG, "onSuccess: " + data);
                    ArrayList<MealEntity> mealsList = new ArrayList<>();
                    Observable.fromIterable(data).subscribe((item) -> {
                        mealsRemoteDataSource.getMealById(Integer.parseInt(item.getId())
                                , new MyCallBack<MealEntity>() {
                                    @Override
                                    public void onSuccess(MealEntity data) {
                                        Log.i(TAG, "Adding to list: " + data);
                                        mealsList.add(data);
                                        callBack.onSuccess(mealsList);
                                    }

                                    @Override
                                    public void onFailure(Failure failure) {
                                        Log.i(TAG + "getMealById", "onFailure:");
                                    }
                                });
                    }, (error) -> {
                        Log.i(TAG, "onError: " + error);
                        callBack.onFailure(FailureHandler.handle(error, "searchMeals"));
                    }, () -> {
                        Log.i(TAG, "onComplete: " + mealsList);
                        callBack.onSuccess(mealsList);
                    });
                }

                @Override
                public void onFailure(Failure failure) {
                    callBack.onFailure(failure);
                }
            });
        }
    }
}
