package com.dmy.foodplannerapp.data.meals.repo;

import com.dmy.foodplannerapp.data.model.dto.CategoryDTO;
import com.dmy.foodplannerapp.data.model.dto.CountryDTO;
import com.dmy.foodplannerapp.data.model.dto.IngredientDTO;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsRepo {
    Single<MealEntity> getMealById(String id);

    Single<MealEntity> getMealOfTheDay();

    Single<List<MealEntity>> getRandomMeals(int quantity);

    Completable addToFavourite(MealEntity meal);

    Completable removeFromFavourite(MealEntity meal);

    Flowable<List<MealEntity>> getFavouriteMeals();

    Flowable<List<MealPlanWithDetails>> getMealsPlansByDate(Date date);

    Flowable<List<Date>> getPlanDatesWithMeals(Date startDate, Date endDate);

    Single<List<MealPlan>> getMealsPlans();

    Completable addMealPlan(MealPlan mealPlan);

    Completable removeMealPlan(MealPlan mealPlan);

    Completable removeMealPlanById(int id);

    Single<List<CategoryDTO>> getCategories();

    Single<List<IngredientDTO>> getIngredients();

    Single<List<CountryDTO>> getCountries();

    Single<List<SearchedMealResponse>> searchMeals(SearchModel searchModel);

    Single<List<SearchedMealResponse>> searchMealsByName(String query);

    Single<List<SearchedMealResponse>> searchMeals(List<SearchModel> filters);

    Completable syncAll();

    Completable downloadAll();
}
