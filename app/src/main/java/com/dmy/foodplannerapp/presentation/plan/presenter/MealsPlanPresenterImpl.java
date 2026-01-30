package com.dmy.foodplannerapp.presentation.plan.presenter;

import android.content.Context;
import android.util.Log;

import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.presentation.plan.view.PlanView;

import java.util.Date;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MealsPlanPresenterImpl implements MealsPlanPresenter {
    private static final String TAG = "MealsPlanPresenterImpl";
    private final MealsRepo mealsRepo;
    private final PlanView view;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public MealsPlanPresenterImpl(Context context, PlanView view) {
        this.mealsRepo = new MealsRepoImpl(context);
        this.view = view;
    }

    @Override
    public void getMealsByDate(Date date) {
        disposables.add(mealsRepo.getMealsPlansByDate(date)
                .subscribe(
                        meals -> view.onMealsLoaded(meals),
                        error -> view.onError(error.getMessage())
                )
        );
    }

    @Override
    public void getDatesWithMeals(Date startDate, Date endDate) {
        disposables.add(mealsRepo.getPlanDatesWithMeals(startDate, endDate)
                .subscribe(
                        dates -> view.onDatesWithMealsLoaded(dates),
                        error -> view.onError(error.getMessage())
                )
        );
    }

    @Override
    public void removeMealPlan(MealPlan mealPlan) {
        disposables.add(mealsRepo.removeMealPlan(mealPlan)
                .subscribe(
                        () -> view.onMealRemoved(),
                        error -> Log.e(TAG, "Error removing meal plan", error)
                )
        );
    }

    @Override
    public void removeMealPlanById(int id) {
        disposables.add(mealsRepo.removeMealPlanById(id).subscribe(
                        () -> view.onMealRemoved(),
                        error -> Log.e(TAG, "Error removing meal plan", error)
                )
        );
    }

    public void dispose() {
        disposables.clear();
    }
}
