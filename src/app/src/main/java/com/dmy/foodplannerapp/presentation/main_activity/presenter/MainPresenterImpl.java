package com.dmy.foodplannerapp.presentation.main_activity.presenter;

import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;
import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.presentation.main_activity.view.MainView;

public class MainPresenterImpl implements MainPresenter {
    AuthRepo authRepo;
    MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;

        this.authRepo = new AuthRepoImp(new MyCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean isLoggedIn) {
                mainView.onLoad(isLoggedIn);
            }

            @Override
            public void onFailure(Failure failure) {
                mainView.onLoad(false);
            }
        });
    }

    @Override
    public void getUserLoginStatus() {
        authRepo.checkIfUserIsLoggedIn();
    }
}
