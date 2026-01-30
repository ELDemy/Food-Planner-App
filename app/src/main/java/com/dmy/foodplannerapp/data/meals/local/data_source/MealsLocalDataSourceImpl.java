package com.dmy.foodplannerapp.data.meals.local.data_source;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.db.AppDatabase;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.local.daos.FavouriteMealsDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealOfTheDayDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsDao;
import com.dmy.foodplannerapp.data.meals.local.daos.MealsPlanDao;
import com.dmy.foodplannerapp.data.model.entity.FavoriteMeal;
import com.dmy.foodplannerapp.data.model.entity.FavoriteMealWithDetails;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealOfTheDay;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private static final String TAG = "MealsLocalDataSourceImp";

    FavouriteMealsDao favouriteMealsDao;
    MealOfTheDayDao mealOfTheDayDao;
    MealsDao mealsDao;
    Handler mainHandler = new Handler(Looper.getMainLooper());
    MealsPlanDao mealsPlanDao;

    public MealsLocalDataSourceImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mealsDao = db.mealsDao();
        mealOfTheDayDao = db.mealOfTheDayDao();
        favouriteMealsDao = db.favMealsDao();
        mealsPlanDao = db.mealPlanDao();
    }

    @Override
    public void getMealOfTheDay(MyCallBack<MealEntity> callBack) {
        Thread th = new Thread(() -> {
            try {
                MealOfTheDay mealOfTheDay = mealOfTheDayDao.get();
                Log.i("TAG", "isFavourite: " + mealOfTheDay);
                if (mealOfTheDay != null) {
                    if (mealOfTheDay.day == LocalDate.now().getDayOfMonth()) {
                        mainHandler.post(() -> callBack.onSuccess(mealOfTheDay.meal));
                    } else {
                        mealOfTheDayDao.delete();
                        mainHandler.post(() -> callBack.onFailure(null));
                    }
                } else {
                    mainHandler.post(() -> callBack.onFailure(null));
                }
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getMealOfTheDay")));
            }
        });
        th.start();
    }

    @Override
    public void addMealOfTheDay(MealEntity meal) {
        Thread th = new Thread(() -> {
            try {
                MealOfTheDay mealOfTheDay = new MealOfTheDay();
                mealOfTheDay.setMeal(meal);
                mealOfTheDay.setDay(LocalDate.now().getDayOfMonth());

                mealOfTheDayDao.insert(mealOfTheDay);
            } catch (Exception e) {
                FailureHandler.handle(e, "addMealOfTheDay");
            }
        });
        th.start();
    }

    @Override
    public void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        FavoriteMeal mealFavorite = new FavoriteMeal(meal.getId());
        new Thread(() -> {
            try {
                meal.setFavourite(true);
                mealsDao.insert(meal);
                favouriteMealsDao.addToFavourite(mealFavorite);

                if (callBack == null) return;

                mainHandler.post(() -> callBack.onSuccess(true));
            } catch (Exception e) {
                if (callBack == null) return;

                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "addToFavourite")));
            }
        }).start();
    }

    @Override
    public void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        new Thread(() -> {
            try {
                meal.setFavourite(false);
                favouriteMealsDao.removeFromFavourite(meal.getId());
                mainHandler.post(() -> callBack.onSuccess(false));
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "addToFavourite")));
            }
        }).start();
    }

    @Override
    public void isFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        Thread th = new Thread(() -> {
            try {
                FavoriteMealWithDetails favouriteMeal = favouriteMealsDao.getById(meal.getId());
                Log.i("TAG", "isFavourite: " + favouriteMeal);
                if (favouriteMeal != null) {
                    mainHandler.post(() -> callBack.onSuccess(true));
                } else {
                    mainHandler.post(() -> callBack.onSuccess(false));
                }
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getFavouriteMeals")));
            }
        });
        th.start();
    }

    @Override
    public void getFavouriteMeals(MyCallBack<LiveData<List<MealEntity>>> callBack) {
        try {
            LiveData<List<FavoriteMealWithDetails>> favMeals = favouriteMealsDao.getAll();
            LiveData<List<MealEntity>> meals = Transformations.map(favMeals,
                    (list) -> {
                        List<MealEntity> mealsList = new ArrayList<>();
                        for (FavoriteMealWithDetails item : list) {
                            item.meal.setFavourite(true);
                            mealsList.add(item.meal);
                        }
                        return mealsList;
                    });
            callBack.onSuccess(meals);
        } catch (Exception e) {
            callBack.onFailure(FailureHandler.handle(e, "getFavouriteMeals"));
        }

    }

    @Override
    public Completable clearAllFavorites() {
        return favouriteMealsDao.clearAll();
    }

    @Override
    public Completable clearAllPlans() {
        return mealsPlanDao.clearAll();
    }

    @Override
    public Single<List<MealPlan>> getMealsPlans() {
        return mealsPlanDao.getMealsPlans()
                .map(plansWithDetails -> {
                            List<MealPlan> mealPlans = new ArrayList<>();
                            for (MealPlanWithDetails item : plansWithDetails) {
                                MealPlan mealPlan = item.plan;
                                mealPlan.setMeal(item.meal);
                                mealPlans.add(item.plan);
                            }
                            return mealPlans;
                        }
                );
    }

    @Override
    public void getMealsPlansByDate(Date date, MyCallBack<LiveData<List<MealPlanWithDetails>>> callBack) {
        Thread th = new Thread(() -> {
            try {
                Log.i(TAG, "getMealsByDate: " + date);
                Log.i(TAG, "getMealsByDate: " + date.getTime());

                LiveData<List<MealPlanWithDetails>> mealPlanWithDetails = mealsPlanDao.getMealsByDate(date);
                mainHandler.post(() -> {
                    callBack.onSuccess(mealPlanWithDetails);
                });
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getMealsByDate")));
            }
        });
        th.start();
    }

    @Override
    public void getPlansDatesWithMeals(Date startDate, Date endDate, MyCallBack<LiveData<List<Date>>> callBack) {
        Thread th = new Thread(() -> {
            try {
                LiveData<List<Date>> dates = mealsPlanDao.getDatesWithMeals(startDate, endDate);
                mainHandler.post(() -> callBack.onSuccess(dates));
            } catch (Exception e) {
                mainHandler.post(() -> callBack.onFailure(FailureHandler.handle(e, "getDatesWithMeals")));
            }
        });
        th.start();
    }


    @Override
    public void addMealPlan(MealPlan mealPlan) {
        Thread th = new Thread(() -> {
            try {
                mealsDao.insert(mealPlan.getMeal());
                mealsPlanDao.insert(mealPlan);
            } catch (Exception e) {
                FailureHandler.handle(e, "addMealPlan");
            }
        });
        th.start();
    }

    @Override
    public void removeMealPlan(MealPlan mealPlan) {
        Thread th = new Thread(() -> {
            try {
                mealsPlanDao.delete(mealPlan);
            } catch (Exception e) {
                FailureHandler.handle(e, "removeMealPlan");
            }
        });
        th.start();
    }

    @Override
    public void removeMealPlanById(int id) {
        Thread th = new Thread(() -> {
            try {
                mealsPlanDao.deleteById(id);
            } catch (Exception e) {
                FailureHandler.handle(e, "removeMealPlanById");
            }
        });
        th.start();
    }


}
