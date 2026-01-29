package com.dmy.foodplannerapp.data.model.dto;

import java.util.List;

public class CategoriesResponse {
    List<CategoryDTO> categories;

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "CategoriesResponse{" +
                "categories=" + categories +
                '}';
    }
}
