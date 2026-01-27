package com.dmy.foodplannerapp.data.model.dto;

import java.util.List;

public class ListOfSearchMealResponse {
    private List<SearchedMealResponse> meals;

    public List<SearchedMealResponse> getMeals() {
        return meals;
    }

    public void setMeals(List<SearchedMealResponse> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "ListOfSearchMealResponse{" +
                "meals=" + meals +
                '}';
    }
}
