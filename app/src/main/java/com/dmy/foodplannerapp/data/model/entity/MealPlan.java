package com.dmy.foodplannerapp.data.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "mealsPlan")
public class MealPlan {

    @Ignore
    private MealEntity meal;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String mealId;
    private MealType mealType;

    public MealPlan() {
    }

    public MealPlan(Date date, String mealId, MealType mealType, MealEntity meal) {
        this.date = date;
        this.mealId = mealId;
        this.mealType = mealType;
        this.meal = meal;
    }

    public MealEntity getMeal() {
        return meal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = MealType.fromValue(mealType);
    }

    public enum MealType {
        BREAKFAST("breakfast"),
        LUNCH("lunch"),
        DINNER("dinner");

        private final String value;

        MealType(String value) {
            this.value = value;
        }

        public static MealType fromValue(String value) {
            for (MealType type : values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return null; // or throw
        }

        public String getValue() {
            return value;
        }
    }
}

