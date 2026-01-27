package com.dmy.foodplannerapp.presentation.home.search_screen_fragment.presenter;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.search_repo.SearchRepo;
import com.dmy.foodplannerapp.data.meals.repo.search_repo.SearchRepoImpl;
import com.dmy.foodplannerapp.data.model.ArgumentSearchScreenModel;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.presentation.home.search_screen_fragment.view.SearchView;

import java.util.List;

public class SearchPresenterImpl implements SearchPresenter {
    SearchRepo searchRepo;
    SearchView view;

    public SearchPresenterImpl(SearchView view) {
        searchRepo = new SearchRepoImpl();
        this.view = view;
    }

    @Override
    public void searchMeals(ArgumentSearchScreenModel arguments) {
        searchRepo.searchMeals(arguments, new MyCallBack<List<MealEntity>>() {
                    @Override
                    public void onSuccess(List<MealEntity> data) {
                        view.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Failure failure) {
                        view.onFailure(failure);
                    }
                }
        );
    }
}
