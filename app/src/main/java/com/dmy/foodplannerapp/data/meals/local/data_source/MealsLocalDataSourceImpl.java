package com.dmy.foodplannerapp.data.meals.local.data_source;

import android.content.Context;
import android.util.Log;

import com.dmy.foodplannerapp.data.db.AppDatabase;
import com.dmy.foodplannerapp.data.meals.local.daos.FavouriteMealsDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealOfTheDayDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsPlanDao;
import com.dmy.foodplannerapp.data.model.entity.FavoriteMeal;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealOfTheDay;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private static final String TAG = "MealsLocalDataSourceImp";

    private final FavouriteMealsDao favouriteMealsDao;
    private final MealOfTheDayDao mealOfTheDayDao;
    private final MealsDao mealsDao;
    private final MealsPlanDao mealsPlanDao;

    public MealsLocalDataSourceImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mealsDao = db.mealsDao();
        mealOfTheDayDao = db.mealOfTheDayDao();
        favouriteMealsDao = db.favMealsDao();
        mealsPlanDao = db.mealPlanDao();
    }

    @Override
    public Single<MealEntity> getMealOfTheDay() {
        return mealOfTheDayDao.get()
                .flatMap(mealOfTheDay -> {
                    Log.i(TAG, "getMealOfTheDay: " + mealOfTheDay);
                    if (mealOfTheDay.day == LocalDate.now().getDayOfMonth()) {
                        return Maybe.just(mealOfTheDay.meal);
                    } else {
                        return mealOfTheDayDao.delete().andThen(Maybe.empty());
                    }
                })
                .toSingle();
    }

    @Override
    public Completable addMealOfTheDay(MealEntity meal) {
        MealOfTheDay mealOfTheDay = new MealOfTheDay();
        mealOfTheDay.setMeal(meal);
        mealOfTheDay.setDay(LocalDate.now().getDayOfMonth());
        return mealOfTheDayDao.insert(mealOfTheDay);
    }

    @Override
    public Completable addToFavourite(MealEntity meal) {
        meal.setFavourite(true);
        FavoriteMeal mealFavorite = new FavoriteMeal(meal.getId());
        return mealsDao.insert(meal)
                .andThen(favouriteMealsDao.addToFavourite(mealFavorite));
    }

    @Override
    public Completable removeFromFavourite(MealEntity meal) {
        meal.setFavourite(false);
        return favouriteMealsDao.removeFromFavourite(meal.getId());
    }

    @Override
    public Single<Boolean> isFavourite(MealEntity meal) {
        return favouriteMealsDao.getById(meal.getId())
                .map(result -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Flowable<List<MealEntity>> getFavouriteMeals() {
        return favouriteMealsDao.getAll()
                .map(list -> {
                    List<MealEntity> meals = new ArrayList<>();
                    for (var item : list) {
                        item.meal.setFavourite(true);
                        meals.add(item.meal);
                    }
                    return meals;
                });
    }

    @Override
    public Completable clearAllFavorites() {
        return favouriteMealsDao.clearAll();
    }

    @Override
    public Flowable<List<MealPlanWithDetails>> getMealsPlansByDate(Date date) {
        Log.i(TAG, "getMealsByDate: " + date);
        return mealsPlanDao.getMealsByDate(date);
    }

    @Override
    public Flowable<List<Date>> getPlansDatesWithMeals(Date startDate, Date endDate) {
        return mealsPlanDao.getDatesWithMeals(startDate, endDate);
    }

    @Override
    public Single<List<MealPlan>> getMealsPlans() {
        return mealsPlanDao.getMealsPlans()
                .map(plansWithDetails -> {
                    List<MealPlan> mealPlans = new ArrayList<>();
                    for (MealPlanWithDetails item : plansWithDetails) {
                        MealPlan mealPlan = item.plan;
                        mealPlan.setMeal(item.meal);
                        mealPlans.add(mealPlan);
                    }
                    return mealPlans;
                });
    }

    @Override
    public Completable addMealPlan(MealPlan mealPlan) {
        return mealsDao.insert(mealPlan.getMeal())
                .andThen(mealsPlanDao.insert(mealPlan));
    }

    @Override
    public Completable removeMealPlan(MealPlan mealPlan) {
        return mealsPlanDao.delete(mealPlan);
    }

    @Override
    public Completable removeMealPlanById(int id) {
        return mealsPlanDao.deleteById(id);
    }

    @Override
    public Completable clearAllPlans() {
        return mealsPlanDao.clearAll();
    }
}
