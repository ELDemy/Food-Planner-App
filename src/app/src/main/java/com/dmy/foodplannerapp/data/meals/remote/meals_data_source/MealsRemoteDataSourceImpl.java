package com.dmy.foodplannerapp.data.meals.remote.meals_data_source;

import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;
import com.dmy.foodplannerapp.data.model.dto.MealDto;
import com.dmy.foodplannerapp.data.model.dto.MealsResponse;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.mapper.MealMapper;
import com.dmy.foodplannerapp.data.network.MealsNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {
    MealsService mealsService;

    public MealsRemoteDataSourceImpl() {
        mealsService = MealsNetwork.getInstance().mealsService;
    }

    @Override
    public Single<MealEntity> getMealById(String id) {
        return mealsService.getMealById(id)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    MealDto mealDto = response.getMeal();
                    if (mealDto != null) {
                        return MealMapper.toEntity(mealDto);
                    }
                    throw new Exception("No meal found with this ID");
                });
    }

    @Override
    public Single<ListOfSearchMealResponse> getMealsByName(String query) {
        return mealsService.getMealByName(query);
    }

    @Override
    public Single<MealDto> getRandomMeal() {
        return mealsService.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .timeout(2, TimeUnit.SECONDS)
                .map(MealsResponse::getMeal);
    }

    @Override
    public Single<List<MealDto>> getRandomMeals(int quantity) {
        return Observable
                .range(0, quantity * 3)
                .subscribeOn(Schedulers.io())
                .flatMapSingle(
                        i -> getRandomMeal().onErrorResumeNext(error -> Single.never())
                )
                .distinct(MealDto::getId)
                .take(quantity)
                .toList()
                .timeout(5, TimeUnit.SECONDS)
                .onErrorReturn(error -> new ArrayList<>());
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

    @Override
    public Single<List<SearchedMealResponse>> getCategoryMeals(String query) {
        return mealsService.getCategoryMeals(query)
                .subscribeOn(Schedulers.io())
                .map(ListOfSearchMealResponse::getMeals);
    }

    @Override
    public Single<List<SearchedMealResponse>> getCountryMeals(String query) {
        return mealsService.getCountryMeals(query)
                .subscribeOn(Schedulers.io())
                .map(ListOfSearchMealResponse::getMeals);

    }

    @Override
    public Single<List<SearchedMealResponse>> getIngredientMeals(String query) {
        return mealsService.getIngredientMeals(query)
                .subscribeOn(Schedulers.io())
                .map(ListOfSearchMealResponse::getMeals);
    }
}
