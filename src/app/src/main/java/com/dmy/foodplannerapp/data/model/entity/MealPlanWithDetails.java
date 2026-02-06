package com.dmy.foodplannerapp.data.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MealPlanWithDetails {
    @Embedded
    public MealPlan plan;

    @Relation(
            parentColumn = "mealId",
            entityColumn = "idMeal"
    )
    public MealEntity meal;

    @Override
    public String toString() {
        return "MealPlanWithDetails{" +
                "plan=" + plan +
                ", meal=" + meal +
                '}';
    }
}
