package com.dmy.foodplannerapp.presentation.home.categories_list_fragment.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.presentation.home.categories_list_fragment.view.CategoriesListView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class CategoriesListPresenterImpl implements CategoriesListPresenter {
    private static final String TAG = "CategoriesListPresenter";
    private final MealsRepo mealsRepo;
    private final CategoriesListView view;

    public CategoriesListPresenterImpl(CategoriesListView view, Context context) {
        this.mealsRepo = new MealsRepoImpl(context);
        this.view = view;
    }

    @Override
    public void getCategories() {
        view.onLoading(true);
        mealsRepo.getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            view.onLoading(false);
                            view.updateCategories(data);
                        }
                        , error -> {
                            view.onLoading(false);
                            view.onFailure(error.getMessage());
                        }
                );
    }
}
