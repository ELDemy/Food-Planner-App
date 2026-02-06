package com.dmy.foodplannerapp.data.model.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;


@Entity(
        tableName = "favorite_meals",
        foreignKeys = @ForeignKey(
                entity = MealEntity.class,
                parentColumns = "idMeal",
                childColumns = "mealId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("mealId")
)
public class FavoriteMeal {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String mealId;

    public FavoriteMeal() {
    }

    public FavoriteMeal(String mealId) {
        this.mealId = mealId;
    }
}

