package com.dmy.foodplannerapp.presentation.auth.presenter;

import android.app.Activity;

import com.dmy.foodplannerapp.data.auth.remote.data_source.OnAuthCallBack;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.CustomAuthCredentials;
import com.dmy.foodplannerapp.data.model.User;
import com.dmy.foodplannerapp.presentation.auth.view.AuthView;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenterImp implements AuthPresenter {
    AuthView authView;
    AuthRepo authRepo;

    public AuthPresenterImp(AuthView authView) {
        this.authView = authView;
        initRemote();
    }

    private void initRemote() {

        authRepo = new AuthRepoImp(
                new OnAuthCallBack() {
                    @Override
                    public void onAuthSuccess(FirebaseUser user) {
                        authView.onAuthLoading(false);

                        User newUser = new User(user.getDisplayName(), user.getEmail());
                        authView.onAuthSuccess(newUser);
                    }

                    @Override
                    public void onAuthFailure(Failure failure) {
                        authView.onAuthLoading(false);

                        authView.onAuthFailure(failure);
                    }
                }
        );
    }

    @Override
    public void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
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
}
