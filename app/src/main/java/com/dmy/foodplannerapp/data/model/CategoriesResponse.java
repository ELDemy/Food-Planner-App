package com.dmy.foodplannerapp.data.model;

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
