package com.dmy.foodplannerapp.data.meals.repo.categories_repo;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSource;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.model.entity.CategoryEntity;

import java.util.List;

public class CategoriesRepoImpl implements CategoriesRepo {
    MealsRemoteDataSource mealsRemoteDataSource;

    public CategoriesRepoImpl() {
        mealsRemoteDataSource = new MealsRemoteDataSourceImpl();
    }

    @Override
    public void getCategories(MyCallBack<List<CategoryEntity>> callBack) {
        mealsRemoteDataSource.getCategories(callBack);
    }
}
