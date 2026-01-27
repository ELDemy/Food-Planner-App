package com.dmy.foodplannerapp.data.meals.repo.categories_repo;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.entity.CategoryEntity;

import java.util.List;

public interface CategoriesRepo {
    void getCategories(MyCallBack<List<CategoryEntity>> callBack);
}
