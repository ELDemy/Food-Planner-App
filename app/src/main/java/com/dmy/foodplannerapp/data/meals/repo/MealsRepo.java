package com.dmy.foodplannerapp.data.meals.repo;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.dto.CategoryDTO;
import com.dmy.foodplannerapp.data.model.dto.CountryDTO;
import com.dmy.foodplannerapp.data.model.dto.IngredientDTO;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealsRepo {
    Single<MealEntity> getMealById(String id);

    void getMealOfTheDay(MyCallBack<MealEntity> callBack);

    Single<List<MealEntity>> getRandomMeals(int quantity);

    void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    void getFavouriteMeals(MyCallBack<LiveData<List<MealEntity>>> callBack);

    void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack);

    Single<List<CategoryDTO>> getCategories();

    Single<List<IngredientDTO>> getIngredients();

    Single<List<CountryDTO>> getCountries();

    Single<List<SearchedMealResponse>> searchMeals(SearchModel searchModel);

    Single<List<SearchedMealResponse>> searchMeals(String query);

    Single<List<SearchedMealResponse>> searchMeals(List<SearchModel> filters);
}
