package com.dmy.foodplannerapp.presentation.auth.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.dmy.foodplannerapp.data.auth.remote.model.CustomAuthCredentials;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;
import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.User;
import com.dmy.foodplannerapp.presentation.auth.view.AuthView;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenterImp implements AuthPresenter {
    private static final String TAG = "AuthPresenterImp";
    AuthView authView;
    AuthRepo authRepo;
    MealsRepo mealsRepo;
    CustomAuthCredentials customAuthCredentials = null;

    public AuthPresenterImp(AuthView authView, Context context) {
        this.authView = authView;
        mealsRepo = new MealsRepoImpl(context);
        initRemote();
    }

    private void initRemote() {
        authRepo = new AuthRepoImp(
                new MyCallBack<FirebaseUser>() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        mealsRepo.downloadAll()
                                .subscribe(
                                        () -> Log.i(TAG, "Download completed"),
                                        error -> Log.e(TAG, "Download failed", error)
                                );
                        authView.onAuthLoading(false);
                        User newUser = setUserDoc(user);
                        authView.onAuthSuccess(newUser);
                    }

                    @Override
                    public void onFailure(Failure failure) {
                        authView.onAuthLoading(false);

                        authView.onAuthFailure(failure);
                    }
                });
    }

    private User setUserDoc(FirebaseUser user) {
        String userName;
        if (customAuthCredentials == null) {
            userName = user.getDisplayName();
        } else {
            userName = customAuthCredentials.getName();
        }

        User newUser = new User(userName, user.getEmail());
        authRepo.setUserData(newUser, new MyCallBack<>() {
            @Override
            public void onSuccess(Boolean data) {

            }

            @Override
            public void onFailure(Failure failure) {

            }
        });
        return newUser;
    }

    @Override
    public void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        this.customAuthCredentials = customAuthCredentials;
        authView.onAuthLoading(true);
        authRepo.signUpWithEmailAndPassword(customAuthCredentials);
    }

    @Override
    public void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        authView.onAuthLoading(true);
        authRepo.signInWithEmailAndPassword(customAuthCredentials);
    }

    @Override
    public void signInWithGoogle(Activity activity) {
        authView.onAuthLoading(true);
        authRepo.signInWithGoogle(activity);
    }

    @Override
    public void continueAsGuest() {
        authView.onAuthLoading(true);
        authRepo.continueAsGuest();
    }
}
