package com.dmy.foodplannerapp.data.meals.repo.ingredients_repo;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSource;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.model.IngredientDTO;

import java.util.List;

public class IngredientsRepoImpl implements IngredientsRepo {
    MealsRemoteDataSource mealsRemoteDataSource;

    public IngredientsRepoImpl() {
        mealsRemoteDataSource = new MealsRemoteDataSourceImpl();
    }

    @Override
    public void getIngredients(MyCallBack<List<IngredientDTO>> callBack) {
        mealsRemoteDataSource.getIngredients(callBack);
    }
}
