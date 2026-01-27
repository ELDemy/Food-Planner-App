package com.dmy.foodplannerapp.presentation.home.categories_list_fragment.view;

import com.dmy.foodplannerapp.data.model.entity.CategoryEntity;

import java.util.List;

public interface CategoriesListView {
    void onLoading(boolean isLoading);

    void onFailure(String message);

    void updateCategories(List<CategoryEntity> categories);
}
