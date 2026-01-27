package com.dmy.foodplannerapp.data.meals.repo.ingredients_repo;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.model.dto.IngredientDTO;

import java.util.List;

public interface IngredientsRepo {
    void getIngredients(MyCallBack<List<IngredientDTO>> callBack);
}
