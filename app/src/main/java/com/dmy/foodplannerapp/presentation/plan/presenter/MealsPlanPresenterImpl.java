package com.dmy.foodplannerapp.presentation.plan.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;
import com.dmy.foodplannerapp.presentation.plan.view.PlanView;

import java.util.Date;
import java.util.List;

public class MealsPlanPresenterImpl implements MealsPlanPresenter {
    private static final String TAG = "MealsPlanPresenterImpl";
    private final MealsRepo mealsRepo;
    private final PlanView view;

    public MealsPlanPresenterImpl(Context context, PlanView view) {
        this.mealsRepo = new MealsRepoImpl(context);
        this.view = view;
    }

    @Override
    public void getMealsByDate(Date date) {
        mealsRepo.getMealsPlansByDate(date, new MyCallBack<>() {
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
        mealsRepo.getPlanDatesWithMeals(startDate, endDate, new MyCallBack<>() {
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
        mealsRepo.removeMealPlan(mealPlan);
        view.onMealRemoved();
    }

    @Override
    public void removeMealPlanById(int id) {
        mealsRepo.removeMealPlanById(id);
        view.onMealRemoved();
    }
}
