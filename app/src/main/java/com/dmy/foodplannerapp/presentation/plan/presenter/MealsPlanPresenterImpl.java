package com.dmy.foodplannerapp.presentation.plan.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.meals_plan_repo.MealsPlanRepo;
import com.dmy.foodplannerapp.data.meals.repo.meals_plan_repo.MealsPlanRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;
import com.dmy.foodplannerapp.presentation.plan.view.PlanView;

import java.util.Date;
import java.util.List;

public class MealsPlanPresenterImpl implements MealsPlanPresenter {
    private static final String TAG = "MealsPlanPresenterImpl";
    private final MealsPlanRepo mealsPlanRepo;
    private final PlanView view;

    public MealsPlanPresenterImpl(Context context, PlanView view) {
        this.mealsPlanRepo = new MealsPlanRepoImpl(context);
        this.view = view;
    }

    @Override
    public void getMealsByDate(Date date) {
        mealsPlanRepo.getMealsByDate(date, new MyCallBack<>() {
            @Override
            public void onSuccess(LiveData<List<MealPlanWithDetails>> data) {
                view.onMealsLoaded(data);
            }

            @Override
            public void onFailure(Failure failure) {
                view.onError(failure.getMessage());
            }
        });
    }

    @Override
    public void getDatesWithMeals(Date startDate, Date endDate) {
        mealsPlanRepo.getDatesWithMeals(startDate, endDate, new MyCallBack<>() {
            @Override
            public void onSuccess(LiveData<List<Date>> data) {
                view.onDatesWithMealsLoaded(data);
            }

            @Override
            public void onFailure(Failure failure) {
                view.onError(failure.getMessage());
            }
        });
    }


    @Override
    public void removeMealPlan(MealPlan mealPlan) {
        mealsPlanRepo.removeMealPlan(mealPlan);
        view.onMealRemoved();
    }

    @Override
    public void removeMealPlanById(int id) {
        mealsPlanRepo.removeMealPlanById(id);
        view.onMealRemoved();
    }
}
