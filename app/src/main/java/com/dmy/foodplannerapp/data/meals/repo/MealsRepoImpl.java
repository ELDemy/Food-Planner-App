package com.dmy.foodplannerapp.data.meals.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.meals.local.MealsLocalDataSource;
import com.dmy.foodplannerapp.data.meals.local.MealsLocalDataSourceImpl;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSource;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.meals.remote.search_data_source.SearchDataSource;
import com.dmy.foodplannerapp.data.meals.remote.search_data_source.SearchRemoteDataSourceImpl;
import com.dmy.foodplannerapp.data.model.dto.CategoriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CategoryDTO;
import com.dmy.foodplannerapp.data.model.dto.CountriesResponse;
import com.dmy.foodplannerapp.data.model.dto.CountryDTO;
import com.dmy.foodplannerapp.data.model.dto.IngredientDTO;
import com.dmy.foodplannerapp.data.model.dto.IngredientsResponse;
import com.dmy.foodplannerapp.data.model.dto.ListOfSearchMealResponse;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.data.model.mapper.MealMapper;

import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepoImpl implements MealsRepo {
    private static final String TAG = "MealsRepoImpl";
    MealsRemoteDataSource mealsRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;
    SearchDataSource searchDataSource;

    public MealsRepoImpl(Context context) {
        mealsRemoteDataSource = new MealsRemoteDataSourceImpl();
        mealsLocalDataSource = new MealsLocalDataSourceImpl(context);
        searchDataSource = new SearchRemoteDataSourceImpl();
    }

    @Override
    public Single<MealEntity> getMealById(String id) {
        return mealsRemoteDataSource.getMealById(id)
                .flatMap(this::checkIfMealIsInFavoriteRx)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Single<MealEntity> checkIfMealIsInFavoriteRx(MealEntity meal) {
        return Single.create(emitter -> {
            mealsLocalDataSource.isFavourite(meal, new MyCallBack<>() {
                @Override
                public void onSuccess(Boolean isFavourite) {
                    meal.setFavourite(isFavourite);
                    emitter.onSuccess(meal);
                }

                @Override
                public void onFailure(Failure failure) {
                    emitter.onSuccess(meal);
                }
            });
        });
    }

    @Override
    public void getMealOfTheDay(MyCallBack<MealEntity> callBack) {
        mealsLocalDataSource.getMealOfTheDay(new MyCallBack<>() {
            @Override
            public void onSuccess(MealEntity meal) {
                checkIfMealIsInFavorite(meal, () -> callBack.onSuccess(meal));
            }

            @Override
            public void onFailure(Failure failure) {
                mealsRemoteDataSource.getRandomMeal()
                        .observeOn(Schedulers.io())
                        .map(MealMapper::toEntity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (meal) -> {
                                    mealsLocalDataSource.addMealOfTheDay(meal);
                                    checkIfMealIsInFavorite(meal, () -> callBack.onSuccess(meal));
                                }, (error) -> {
                                    callBack.onFailure(FailureHandler.handle(error, TAG));
                                });
            }
        });
    }

    private void checkIfMealIsInFavorite(MealEntity meal, Runnable onCompleted) {
        mealsLocalDataSource.isFavourite(meal,
                new MyCallBack<>() {
                    @Override
                    public void onSuccess(Boolean isFavourite) {
                        Log.i("TAG", "during update favorite" + isFavourite);
                        meal.setFavourite(isFavourite);
                        onCompleted.run();
                    }

                    @Override
                    public void onFailure(Failure failure) {
                        onCompleted.run();
                    }
                });
    }

    @Override
    public Single<List<MealEntity>> getRandomMeals(int quantity) {
        return mealsRemoteDataSource.getRandomMeals(quantity)
                .flatMapObservable(Observable::fromIterable)
                .map(MealMapper::toEntity)
                .toList();
    }

    @Override
    public void addToFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        mealsLocalDataSource.addToFavourite(meal, callBack);
    }

    @Override
    public void getFavouriteMeals(MyCallBack<LiveData<List<MealEntity>>> callBack) {
        mealsLocalDataSource.getFavouriteMeals(callBack);
    }

    @Override
    public void removeFromFavourite(MealEntity meal, MyCallBack<Boolean> callBack) {
        mealsLocalDataSource.removeFromFavourite(meal, callBack);
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

        if (searchType == SearchModel.SearchType.CATEGORY) {
            return searchDataSource.getCategoryMeals(arguments.name);
        }
        if (searchType == SearchModel.SearchType.COUNTRY) {
            return searchDataSource.getCountryMeals(arguments.name);
        }
        if (searchType == SearchModel.SearchType.INGREDIENT) {
            return searchDataSource.getIngredientMeals(arguments.name);
        }

        return Single.just(List.of());
    }

    @Override
    public Single<List<SearchedMealResponse>> searchMeals(String query) {
        return mealsRemoteDataSource.getMealsByName(query)
                .subscribeOn(Schedulers.io())
                .map(ListOfSearchMealResponse::getMeals);
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

        List<SearchedMealResponse> intersection = new java.util.ArrayList<>(allResults.get(0));

        for (int i = 1; i < allResults.size(); i++) {
            List<SearchedMealResponse> currentList = allResults.get(i);
            Set<String> currentIds = new java.util.HashSet<>();
            for (SearchedMealResponse meal : currentList) {
                currentIds.add(meal.getId());
            }
            intersection.removeIf(meal -> !currentIds.contains(meal.getId()));
        }

        return intersection;
    }
}
