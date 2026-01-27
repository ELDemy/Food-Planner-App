package com.dmy.foodplannerapp.data.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.dmy.foodplannerapp.data.model.entity.MealEntity;

@Entity
public class MealOfTheDay {
    @PrimaryKey
    public int day;
    @Embedded
    public MealEntity meal;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public MealEntity getMeal() {
        return meal;
    }

    public void setMeal(MealEntity meal) {
        this.meal = meal;
    }


}
