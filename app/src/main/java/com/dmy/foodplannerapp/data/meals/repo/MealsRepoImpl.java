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
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;

import java.util.ArrayList;
import java.util.List;

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
    public void getMealById(int id, MyCallBack<MealEntity> callBack) {
        mealsRemoteDataSource.getMealById(id, new MyCallBack<>() {
            @Override
            public void onSuccess(MealEntity meal) {
                checkIfMealIsInFavorite(meal, () -> callBack.onSuccess(meal));
            }

            @Override
            public void onFailure(Failure failure) {
                callBack.onFailure(failure);
            }
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
                mealsRemoteDataSource.getRandomMeal(new MyCallBack<>() {
                    @Override
                    public void onSuccess(MealEntity meal) {
                        mealsLocalDataSource.addMealOfTheDay(meal);
                        checkIfMealIsInFavorite(meal, () -> callBack.onSuccess(meal));
                    }

                    @Override
                    public void onFailure(Failure failure) {
                        callBack.onFailure(failure);
                    }
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
    public void getRandomMeals(int quantity, MyCallBack<List<MealEntity>> callBack) {
        mealsRemoteDataSource.getRandomMeals(quantity, new MyCallBack<>() {
            @Override
            public void onSuccess(List<MealEntity> meals) {
                for (int i = 0; i < meals.size(); i++) {
                    final int finalI = i;
                    checkIfMealIsInFavorite(meals.get(i),
                            () -> {
                                if (finalI == quantity - 1) {
                                    callBack.onSuccess(meals);
                                }
                            });
                }
            }

            @Override
            public void onFailure(Failure failure) {
                callBack.onFailure(failure);
            }
        });
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
    public void searchMeals(SearchModel arguments, MyCallBack<List<MealEntity>> callBack) {
        SearchModel.SearchType searchType = arguments.getType();

        if (searchType == SearchModel.SearchType.CATEGORY) {
            searchDataSource.getCategoryMeals(arguments.name, new MyCallBack<List<SearchedMealResponse>>() {
                @Override
                public void onSuccess(List<SearchedMealResponse> data) {
                    Log.i(TAG, "onSuccess: " + data);
                    ArrayList<MealEntity> mealsList = new ArrayList<>();
                    Observable.fromIterable(data).subscribe((item) -> {
                        mealsRemoteDataSource.getMealById(Integer.parseInt(item.getId())
                                , new MyCallBack<MealEntity>() {
                                    @Override
                                    public void onSuccess(MealEntity data) {
                                        Log.i(TAG, "Adding to list: " + data);
                                        mealsList.add(data);
                                        callBack.onSuccess(mealsList);
                                    }

                                    @Override
                                    public void onFailure(Failure failure) {
                                        Log.i(TAG + "getMealById", "onFailure:");
                                    }
                                });
                    }, (error) -> {
                        Log.i(TAG, "onError: " + error);
                        callBack.onFailure(FailureHandler.handle(error, "searchMeals"));
                    }, () -> {
                        Log.i(TAG, "onComplete: " + mealsList);
                        callBack.onSuccess(mealsList);
                    });
                }

                @Override
                public void onFailure(Failure failure) {
                    callBack.onFailure(failure);
                }
            });
        }
    }
}
