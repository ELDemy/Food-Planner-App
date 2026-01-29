package com.dmy.foodplannerapp.presentation.search.presenter;

import android.content.Context;
import android.util.Log;

import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.data.model.mapper.MealMapper;
import com.dmy.foodplannerapp.presentation.search.view.SearchView;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private static final String TAG = "SearchPresenterImpl";
    MealsRepo mealsRepo;
    SearchView view;

    public SearchPresenterImpl(SearchView view, Context context) {
        mealsRepo = new MealsRepoImpl(context);
        this.view = view;
    }

    @Override
    public void searchMeals(List<SearchModel> arguments) {
        Log.i(TAG, "searchMeals: " + arguments);
        if (arguments == null || arguments.isEmpty()) {
            showRandomMeals();
            return;
        }

        view.onLoad();
        var x = mealsRepo.searchMeals(arguments)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (data) -> view.onSuccess(data),
                        (error) -> view.onFailure(FailureHandler.handle(error, TAG)));
    }

    @Override
    public void searchMeals(String query, List<SearchModel> arguments) {

        if ((arguments == null || arguments.isEmpty()) && (query != null || !query.isEmpty())) {
            getMealsByName(query);
            return;
        }

        if (query == null || query.trim().isEmpty()) {
            searchMeals(arguments);
            return;
        }
        view.onLoad();
        mealsRepo.searchMeals(arguments)
                .map(data -> {
                    return data.stream()
                            .filter(meal -> meal.getName().toLowerCase().contains(query.toLowerCase()))
                            .collect(Collectors.toList());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (data) -> view.onSuccess(data),
                        (error) -> view.onFailure(FailureHandler.handle(error, TAG)));
    }

    void getMealsByName(String query) {
        mealsRepo.searchMealsByName(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (data) -> view.onSuccess(data),
                        (error) -> {
                            view.onSuccess(List.of());
                            view.onFailure(FailureHandler.handle(error, TAG));
                        }
                );
    }

    private void showRandomMeals() {
        mealsRepo.getRandomMeals(10)
                .observeOn(Schedulers.io())
                .map(data -> data.stream().map(meal -> MealMapper.toSearchDto(meal)).toList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (data) -> view.onSuccess(data),
                        (error) -> view.onFailure(FailureHandler.handle(error, TAG))
                );
    }
}