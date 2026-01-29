package com.dmy.foodplannerapp.presentation.search.presenter.items_list_presenter;

import android.content.Context;
import android.util.Log;

import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.presentation.search.view.items_list.ItemsListView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class ItemsListPresenterImpl implements ItemsListPresenter {
    private static final String TAG = "ItemsListPresenterImpl";
    private final MealsRepo mealsRepo;
    private final ItemsListView view;

    public ItemsListPresenterImpl(ItemsListView view, Context context) {
        mealsRepo = new MealsRepoImpl(context);
        this.view = view;
    }

    @Override
    public void loadItems(SearchModel.SearchType itemType) {
        if (itemType == SearchModel.SearchType.CATEGORY) {
            mealsRepo.getCategories()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (data) -> {
                                Log.i(TAG, "loadItems: " + data);
                                view.onSuccess(data);
                            },
                            (error) -> view.onFailure(FailureHandler.handle(error, ""))
                    );
        }

        if (itemType == SearchModel.SearchType.INGREDIENT) {
            mealsRepo.getIngredients()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (data) -> {
                                Log.i(TAG, "loadItems: " + data);
                                view.onSuccess(data);
                            },
                            (error) -> view.onFailure(FailureHandler.handle(error, ""))
                    );
        }

        if (itemType == SearchModel.SearchType.COUNTRY) {
            mealsRepo.getCountries()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (data) -> {
                                Log.i(TAG, "loadItems: " + data);
                                view.onSuccess(data);
                            },
                            (error) -> view.onFailure(FailureHandler.handle(error, ""))
                    );
        }
    }
}
