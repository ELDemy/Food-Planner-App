package com.dmy.foodplannerapp.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class SearchedMealResponse {
    @SerializedName("strMeal")
    private String name;
    @SerializedName("strMealThumb")
    private String thumbNail;
    @SerializedName("idMeal")
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SearchMealsResponse{" +
                "name='" + name + '\'' +
                ", thumbNail='" + thumbNail + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
