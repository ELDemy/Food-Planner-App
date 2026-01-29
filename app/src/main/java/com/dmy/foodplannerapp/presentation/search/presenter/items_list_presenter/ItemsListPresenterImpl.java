package com.dmy.foodplannerapp.presentation.search.presenter.items_list_presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.dto.FilterItem;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.presentation.search.view.items_list.ItemsListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class ItemsListPresenterImpl implements ItemsListPresenter {
    private static final String TAG = "ItemsListPresenterImpl";
    private final MealsRepo mealsRepo;
    private final ItemsListView view;
    List<FilterItem> items;

    public ItemsListPresenterImpl(ItemsListView view, Context context) {
        items = new ArrayList<>();
        mealsRepo = new MealsRepoImpl(context);
        this.view = view;
    }

    @Override
    public void loadItems(SearchModel.SearchType itemType) {
        if (itemType == SearchModel.SearchType.CATEGORY) {
            mealsRepo.getCategories()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (data) -> onDataSuccess(data),
                            (error) -> view.onFailure(FailureHandler.handle(error, ""))
                    );
        }

        if (itemType == SearchModel.SearchType.INGREDIENT) {
            mealsRepo.getIngredients()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (data) -> onDataSuccess(data),
                            (error) -> view.onFailure(FailureHandler.handle(error, ""))
                    );
        }

        if (itemType == SearchModel.SearchType.COUNTRY) {
            mealsRepo.getCountries()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (data) -> {
                                onDataSuccess(data);
                            },
                            (error) -> view.onFailure(FailureHandler.handle(error, ""))
                    );
        }
    }

    void onDataSuccess(List<? extends FilterItem> items) {
        this.items.clear();
        this.items.addAll(items);

        view.onSuccess(items);
    }

    @Override
    public void filter(String query) {
        List<FilterItem> filtered = items.stream()
                .filter((i) -> i.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();

        view.onSuccess(filtered);
    }
}
