package com.dmy.foodplannerapp.presentation.user_profile.prsenter;

import android.content.Context;
import android.util.Log;

import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;
import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.presentation.user_profile.view.ProfileView;

public class ProfilePresenter {

    private static final String TAG = "ProfilePresenter";
    private final MealsRepo mealsRepo;
    private final ProfileView view;
    private AuthRepo authRepo;

    public ProfilePresenter(Context context, ProfileView view) {
        this.view = view;
        mealsRepo = new MealsRepoImpl(context);
        initRepo();
    }

    private void initRepo() {
        authRepo = new AuthRepoImp(new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.onSignOut();
            }

            @Override
            public void onFailure(Failure failure) {
                view.onFailure(failure.getMessage());
            }
        });
    }

    public void signOut() {
        authRepo.signOut();
    }

    public void sync() {
        mealsRepo.syncAll(new MyCallBack<>() {
            @Override
            public void onSuccess(Boolean data) {
                Log.i(TAG, "on Sync Success: " + data);
                view.onSync();
            }

            @Override
            public void onFailure(Failure failure) {
                Log.i(TAG, "on Sync Failed: " + failure.getMessage());

                view.onFailure(failure.getMessage());
            }
        });

    }
}
