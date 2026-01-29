package com.dmy.foodplannerapp.data.meals.remote;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    void getMealById(int id, MyCallBack<MealEntity> callBack);

    void getRandomMeal(MyCallBack<MealEntity> callBack);

    void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack);

    Single<CategoriesResponse> getCategories();

    Single<IngredientsResponse> getIngredients();

    Single<CountriesResponse> getCountries();
}
