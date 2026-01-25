package com.dmy.foodplannerapp.data.meals.repo.categories_repo;

import android.content.Context;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.meals.local.MealsLocalDataSource;
import com.dmy.foodplannerapp.data.meals.local.MealsLocalDataSourceImpl;
import com.dmy.foodplannerapp.data.model.CategoryEntity;

import java.util.List;

public class CategoriesRepoImpl implements CategoriesRepo {
    MealsLocalDataSource mealsLocalDataSource;

    public CategoriesRepoImpl(Context context) {
        mealsLocalDataSource = new MealsLocalDataSourceImpl(context);
    }

    @Override
    public void getCategories(MyCallBack<List<CategoryEntity>> callBack) {

    }
}
