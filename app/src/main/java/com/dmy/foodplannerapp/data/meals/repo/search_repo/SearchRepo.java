package com.dmy.foodplannerapp.data.meals.repo.search_repo;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;

import java.util.List;

public interface SearchRepo {
    void searchMeals(SearchModel searchModel, MyCallBack<List<MealEntity>> callBack);
}
