package com.dmy.foodplannerapp.data.model.dto;

import androidx.annotation.NonNull;

import java.util.List;

// the response from the meal api
public class MealsResponse {

    private List<MealDto> meals;

    public MealsResponse(List<MealDto> meals) {
        this.meals = meals;
    }

    public List<MealDto> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDto> meals) {
        this.meals = meals;
    }

    public MealDto getMeal() {
        if (meals == null || meals.isEmpty()) {
            return null;
        }

        return meals.get(0);
    }

    @NonNull
    @Override
    public String toString() {
        return meals != null ? meals.toString() : "[]";
    }
}
