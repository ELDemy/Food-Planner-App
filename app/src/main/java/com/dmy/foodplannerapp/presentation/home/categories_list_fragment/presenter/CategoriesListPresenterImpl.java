package com.dmy.foodplannerapp.presentation.home.categories_list_fragment.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.categories_repo.CategoriesRepo;
import com.dmy.foodplannerapp.data.meals.repo.categories_repo.CategoriesRepoImpl;
import com.dmy.foodplannerapp.data.model.CategoryEntity;
import com.dmy.foodplannerapp.presentation.home.categories_list_fragment.view.CategoriesListView;

import java.util.List;

public class CategoriesListPresenterImpl implements CategoriesListPresenter {
    CategoriesRepo categoriesRepo;
    CategoriesListView view;

    public CategoriesListPresenterImpl(Context context, CategoriesListView view) {
        this.categoriesRepo = new CategoriesRepoImpl(context);
        this.view = view;
    }

    @Override
    public void getCategories() {
        view.onLoading(true);
        categoriesRepo.getCategories(new MyCallBack<>() {
            @Override
            public void onSuccess(List<CategoryEntity> categories) {
                view.onLoading(false);
                view.updateCategories(categories);
            }

            @Override
            public void onFailure(Failure failure) {
                view.onLoading(false);
                view.onFailure(failure.getMessage());
            }
        });
    }
}
