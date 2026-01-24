package com.dmy.foodplannerapp.data.meals.remote;

public interface MealsRemoteDataSource {
    void getMealById(int id);

    void getRandomMeal();
}
