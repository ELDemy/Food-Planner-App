package com.dmy.foodplannerapp.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class IngredientDTO {
    @SerializedName("idIngredient")
    private String id;
    @SerializedName("strIngredient")
    private String name;
    @SerializedName("strDescription")
    private String description;
    @SerializedName("strType")
    private String type;
    @SerializedName("strThumb")
    private String thumb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
