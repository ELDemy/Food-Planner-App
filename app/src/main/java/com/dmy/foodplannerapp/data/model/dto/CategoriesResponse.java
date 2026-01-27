package com.dmy.foodplannerapp.data.model.dto;

import com.dmy.foodplannerapp.data.model.entity.CategoryEntity;

import java.util.List;

public class CategoriesResponse {
    List<CategoryEntity> categories;

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "CategoriesResponse{" +
                "categories=" + categories +
                '}';
    }
}
