package com.dmy.foodplannerapp.presentation.home.categories_list_fragment.presenter;

import android.util.Log;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.categories_repo.CategoriesRepo;
import com.dmy.foodplannerapp.data.meals.repo.categories_repo.CategoriesRepoImpl;
import com.dmy.foodplannerapp.data.model.CategoryEntity;
import com.dmy.foodplannerapp.presentation.home.categories_list_fragment.view.CategoriesListView;

import java.util.List;

public class CategoriesListPresenterImpl implements CategoriesListPresenter {
    private static final String TAG = "CategoriesListPresenter";
    private final CategoriesRepo categoriesRepo;
    private final CategoriesListView view;

    public CategoriesListPresenterImpl(CategoriesListView view) {
        this.categoriesRepo = new CategoriesRepoImpl();
        this.view = view;
    }

    @Override
    public void getCategories() {
        view.onLoading(true);
        categoriesRepo.getCategories(new MyCallBack<>() {
            @Override
            public void onSuccess(List<CategoryEntity> categories) {
                Log.i(TAG, "onSuccess: " + categories.size());
                view.onLoading(false);
                view.updateCategories(categories);
            }

            @Override
            public void onFailure(Failure failure) {
                Log.i(TAG, "onFailure: " + failure.getMessage());
                view.onLoading(false);
                view.onFailure(failure.getMessage());
            }
        });
    }
}
