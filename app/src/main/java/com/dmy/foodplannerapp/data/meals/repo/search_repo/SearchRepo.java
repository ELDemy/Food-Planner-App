package com.dmy.foodplannerapp.data.meals.repo.search_repo;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.ArgumentSearchScreenModel;
import com.dmy.foodplannerapp.data.model.MealEntity;

import java.util.List;

public interface SearchRepo {
    void searchMeals(ArgumentSearchScreenModel argumentSearchScreenModel, MyCallBack<List<MealEntity>> callBack);
}
