package com.dmy.foodplannerapp.data.model;

import androidx.annotation.NonNull;

import java.util.List;

// the response from the meal api
public class MealsResponse {

    private List<MealEntity> meals;

    public MealsResponse(List<MealEntity> meals) {
        this.meals = meals;
    }

    public List<MealEntity> getMeals() {
        return meals;
    }

    public void setMeals(List<MealEntity> meals) {
        this.meals = meals;
    }

    public MealEntity getMeal() {
        if (meals.isEmpty()) {
            return null;
        }
        
        return meals.get(0);
    }

    @NonNull
    @Override
    public String toString() {
        return meals.toString();
    }
}
