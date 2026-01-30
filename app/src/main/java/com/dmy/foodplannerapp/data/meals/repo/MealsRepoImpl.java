package com.dmy.foodplannerapp.data.meals.repo;

import android.content.Context;

import com.dmy.foodplannerapp.data.meals.local.data_source.MealsLocalDataSource;
import com.dmy.foodplannerapp.data.meals.local.data_source.MealsLocalDataSourceImpl;
import com.dmy.foodplannerapp.data.meals.remote.firestore.FirestoreRemoteDataSource;
import com.dmy.foodplannerapp.data.meals.remote.meals_data_source.MealsRemoteDataSource;
import com.dmy.foodplannerapp.data.meals.remote.meals_data_source.MealsRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CategoryDTO;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountryDTO;
import com.dmy.foodplannerapp.data.model.dto.IngredientDTO;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.MealPlan;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.data.model.mapper.MealMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepoImpl implements MealsRepo {
    private static final String TAG = "MealsRepoImpl";
    private final MealsRemoteDataSource mealsRemoteDataSource;
    private final MealsLocalDataSource mealsLocalDataSource;
    private final FirestoreRemoteDataSource firestoreRemoteDataSource;

    public MealsRepoImpl(Context context) {
        mealsRemoteDataSource = new MealsRemoteDataSourceImpl();
        mealsLocalDataSource = new MealsLocalDataSourceImpl(context);
        firestoreRemoteDataSource = new FirestoreRemoteDataSource();
    }

    @Override
    public Single<MealEntity> getMealById(String id) {
        return mealsRemoteDataSource.getMealById(id)
                .flatMap(this::checkIfMealIsInFavorite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<MealEntity> getMealOfTheDay() {
        return mealsLocalDataSource.getMealOfTheDay()
                .subscribeOn(Schedulers.io())
                .flatMap(this::checkIfMealIsInFavorite)
                .onErrorResumeNext(error -> {
                    return mealsRemoteDataSource.getRandomMeal()
                            .subscribeOn(Schedulers.io())
                            .map(MealMapper::toEntity)
                            .flatMap(meal -> mealsLocalDataSource.addMealOfTheDay(meal)
                                    .andThen(Single.just(meal)))
                            .flatMap(this::checkIfMealIsInFavorite);
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<MealEntity>> getRandomMeals(int quantity) {
        return mealsRemoteDataSource.getRandomMeals(quantity)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(Observable::fromIterable)
                .map(MealMapper::toEntity)
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Single<MealEntity> checkIfMealIsInFavorite(MealEntity meal) {
        return mealsLocalDataSource.isFavourite(meal)
                .subscribeOn(Schedulers.io())
                .map(isFavourite -> {
                    meal.setFavourite(isFavourite);
                    return meal;
                })
                .onErrorReturn(error -> meal);
    }

    @Override
    public Completable addToFavourite(MealEntity meal) {
        return mealsLocalDataSource.addToFavourite(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable removeFromFavourite(MealEntity meal) {
        return mealsLocalDataSource.removeFromFavourite(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<MealEntity>> getFavouriteMeals() {
        return mealsLocalDataSource.getFavouriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<MealPlanWithDetails>> getMealsPlansByDate(Date date) {
        return mealsLocalDataSource.getMealsPlansByDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<Date>> getPlanDatesWithMeals(Date startDate, Date endDate) {
        return mealsLocalDataSource.getPlansDatesWithMeals(startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<MealPlan>> getMealsPlans() {
        return mealsLocalDataSource.getMealsPlans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable addMealPlan(MealPlan mealPlan) {
        return mealsLocalDataSource.addMealPlan(mealPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable removeMealPlan(MealPlan mealPlan) {
        return mealsLocalDataSource.removeMealPlan(mealPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable removeMealPlanById(int id) {
        return mealsLocalDataSource.removeMealPlanById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<CategoryDTO>> getCategories() {
        return mealsRemoteDataSource.getCategories()
                .subscribeOn(Schedulers.io())
                .map(CategoriesResponse::getCategories)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<IngredientDTO>> getIngredients() {
        return mealsRemoteDataSource.getIngredients()
                .subscribeOn(Schedulers.io())
                .map(IngredientsResponse::getIngredients)
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<List<CountryDTO>> getCountries() {
        return mealsRemoteDataSource.getCountries()
                .subscribeOn(Schedulers.io())
                .map(CountriesResponse::getCountries)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<SearchedMealResponse>> searchMeals(SearchModel arguments) {
        SearchModel.SearchType searchType = arguments.getType();

        Single<List<SearchedMealResponse>> result;
        if (searchType == SearchModel.SearchType.CATEGORY) {
            result = mealsRemoteDataSource.getCategoryMeals(arguments.name);
        } else if (searchType == SearchModel.SearchType.COUNTRY) {
            result = mealsRemoteDataSource.getCountryMeals(arguments.name);
        } else if (searchType == SearchModel.SearchType.INGREDIENT) {
            result = mealsRemoteDataSource.getIngredientMeals(arguments.name);
        } else {
            result = Single.just(List.of());
        }

        return result
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<SearchedMealResponse>> searchMealsByName(String query) {
        return mealsRemoteDataSource.getMealsByName(query)
                .subscribeOn(Schedulers.io())
                .map(ListOfSearchMealResponse::getMeals)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<SearchedMealResponse>> searchMeals(List<SearchModel> filters) {
        if (filters == null || filters.isEmpty()) {
            return Single.just(List.of());
        }

        if (filters.size() == 1) {
            return searchMeals(filters.get(0));
        }

        return Observable.fromIterable(filters)
                .flatMapSingle(this::searchMeals)
                .toList()
                .map(this::intersectResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<SearchedMealResponse> intersectResults(List<List<SearchedMealResponse>> allResults) {
        if (allResults.isEmpty()) {
            return List.of();
        }

        List<SearchedMealResponse> intersection = new ArrayList<>(allResults.get(0));

        for (int i = 1; i < allResults.size(); i++) {
            List<SearchedMealResponse> currentList = allResults.get(i);
            Set<String> currentIds = new HashSet<>();
            for (SearchedMealResponse meal : currentList) {
                currentIds.add(meal.getId());
            }

            intersection.removeIf(meal -> !currentIds.contains(meal.getId()));
        }

        return intersection;
    }

    @Override
    public Completable syncAll() {
        return Completable.mergeArray(
                        syncFavorites(),
                        syncPlans()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable downloadAll() {
        return Completable.mergeArray(
                        downloadFavorites(),
                        downloadMealsPlans()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Completable syncFavorites() {
        return firestoreRemoteDataSource.clearFavorites()
                .andThen(getFavouriteMeals().firstOrError())
                .flatMapCompletable(meals -> firestoreRemoteDataSource.uploadFavorites(meals))
                .onErrorComplete();
    }

    private Completable syncPlans() {
        return firestoreRemoteDataSource.clearPlans()
                .andThen(getMealsPlans())
                .flatMapCompletable(plans -> firestoreRemoteDataSource.uploadPlans(plans))
                .onErrorComplete();
    }

    private Completable downloadFavorites() {
        return mealsLocalDataSource.clearAllFavorites()
                .andThen(firestoreRemoteDataSource.getFavorites())
                .flatMapCompletable(meals -> Observable.fromIterable(meals)
                        .flatMapCompletable(meal -> mealsLocalDataSource.addToFavourite(meal)))
                .onErrorComplete();
    }

    private Completable downloadMealsPlans() {
        return mealsLocalDataSource.clearAllPlans()
                .andThen(firestoreRemoteDataSource.getPlans())
                .flatMapCompletable(plans -> Observable.fromIterable(plans)
                        .flatMapCompletable(plan -> mealsLocalDataSource.addMealPlan(plan)))
                .onErrorComplete();
    }
}
