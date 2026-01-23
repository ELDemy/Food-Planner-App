package com.dmy.foodplannerapp.presentation.splash.presenter;

import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;

public class SplashPresenter {
    AuthRepo authRepo;

    public SplashPresenter() {
        authRepo = new AuthRepoImp(null);
    }

    public boolean checkIfUserIsLoggedIn() {
        return authRepo.checkIfUserIsLoggedIn();
    }
}
