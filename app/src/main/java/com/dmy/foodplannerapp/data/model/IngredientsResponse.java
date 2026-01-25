package com.dmy.foodplannerapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponse {
    @SerializedName("meals")
    List<IngredientDTO> ingredients;

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }
}
