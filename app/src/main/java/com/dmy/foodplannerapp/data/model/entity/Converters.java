package com.dmy.foodplannerapp.data.model.entity;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static MealPlan.MealType fromMealType(String value) {
        return value == null ? null : MealPlan.MealType.fromValue(value);
    }

    @TypeConverter
    public static String mealTypeToString(MealPlan.MealType mealType) {
        return mealType == null ? null : mealType.getValue();
    }
}
