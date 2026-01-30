package com.dmy.foodplannerapp.presentation.splash.presenter;

import android.content.Context;
import android.util.Log;

import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;
import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.presentation.splash.view.SplashView;

public class SplashPresenter {
    private static final String TAG = "SplashPresenter";
    AuthRepo authRepo;
    SplashView splashView;
    MealsRepo mealsRepo;

    public SplashPresenter(SplashView splashView, Context context) {
        this.splashView = splashView;
        mealsRepo = new MealsRepoImpl(context);
        authRepo = new AuthRepoImp(new MyCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                Log.i(TAG, "onSuccess: " + "navigate to home");
                splashView.navigateToHomeScreen();
            }

            @Override
            public void onFailure(Failure failure) {
                splashView.navigateToAuthScreen();
            }
        });


    }

    public void checkIfUserIsLoggedIn() {
        authRepo.checkIfUserIsLoggedIn();
    }

}
