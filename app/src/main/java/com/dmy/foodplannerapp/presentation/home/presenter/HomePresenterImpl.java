package com.dmy.foodplannerapp.presentation.home.presenter;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.MealEntity;

public class HomePresenterImpl implements HomePresenter {

    MealsRepo mealsRepo;
    HomeView homeView;

    public HomePresenterImpl(HomeView homeView) {
        mealsRepo = new MealsRepoImpl();
        this.homeView = homeView;
    }

    @Override
    public void getMealOfTheDay() {
        homeView.loadMealOfTheDay(true);
        mealsRepo.getMealOfTheDay(
                new MyCallBack<>() {
                    @Override
                    public void onSuccess(MealEntity meal) {
                        homeView.loadMealOfTheDay(false);
                        homeView.showMealOfTheDay(meal);
                    }

                    @Override
                    public void onFailure(Failure failure) {
                        homeView.loadMealOfTheDay(false);
                        homeView.errorMealOfTheDay(failure.getMessage());
                    }
                }
        );
    }
}
