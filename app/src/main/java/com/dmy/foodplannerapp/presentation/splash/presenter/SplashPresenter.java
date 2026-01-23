package com.dmy.foodplannerapp.presentation.splash.presenter;

import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;
import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.presentation.splash.view.SplashView;

public class SplashPresenter {
    AuthRepo authRepo;
    SplashView splashView;

    public SplashPresenter(SplashView splashView) {
        this.splashView = splashView;

        authRepo = new AuthRepoImp(new MyCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
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
