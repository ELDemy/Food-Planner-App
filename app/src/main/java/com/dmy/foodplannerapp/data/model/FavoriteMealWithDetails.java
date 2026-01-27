package com.dmy.foodplannerapp.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FavoriteMealWithDetails {

    @Embedded
    public FavoriteMeal favorite;

    @Relation(
            parentColumn = "mealId",
            entityColumn = "idMeal"
    )
    public MealEntity meal;
}
