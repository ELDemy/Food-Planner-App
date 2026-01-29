package com.dmy.foodplannerapp.presentation.search.presenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.data.model.mapper.MealMapper;
import com.dmy.foodplannerapp.presentation.search.view.SearchView;

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
    public void searchMeals(SearchModel arguments) {

        if (arguments == null) {
            showRandomMeals();
            return;
        }

        mealsRepo.searchMeals(arguments)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (data) -> view.onSuccess(data),
                        (error) -> view.onFailure(FailureHandler.handle(error, TAG))
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
