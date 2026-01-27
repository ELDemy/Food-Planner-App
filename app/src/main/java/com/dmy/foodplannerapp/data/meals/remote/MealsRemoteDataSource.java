package com.dmy.foodplannerapp.data.meals.remote;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.dto.IngredientDTO;
import com.dmy.foodplannerapp.data.model.entity.CategoryEntity;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.List;

public interface MealsRemoteDataSource {
    void getMealById(int id, MyCallBack<MealEntity> callBack);

    void getRandomMeal(MyCallBack<MealEntity> callBack);

    void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack);

    void getCategories(MyCallBack<List<CategoryEntity>> callBack);

    void getIngredients(MyCallBack<List<IngredientDTO>> callBack);
}
