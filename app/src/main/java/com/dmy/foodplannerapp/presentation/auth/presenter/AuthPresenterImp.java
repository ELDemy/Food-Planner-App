package com.dmy.foodplannerapp.presentation.auth.presenter;

import android.app.Activity;

import com.dmy.foodplannerapp.data.auth.remote.data_source.OnAuthCallBack;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.CustomAuthCredentials;
import com.dmy.foodplannerapp.data.model.User;
import com.dmy.foodplannerapp.presentation.auth.view.OnAuthCall;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenterImp implements AuthPresenter {
    OnAuthCall onAuthCall;
    AuthRepo authRepo;

    public AuthPresenterImp(OnAuthCall onAuthCall) {
        this.onAuthCall = onAuthCall;
        initRemote();
    }

    private void initRemote() {

        authRepo = new AuthRepoImp(
                new OnAuthCallBack() {
                    @Override
                    public void onAuthSuccess(FirebaseUser user) {
                        onAuthCall.onAuthLoading(false);

                        User newUser = new User(user.getDisplayName(), user.getEmail());
                        onAuthCall.onAuthSuccess(newUser);
                    }

                    @Override
                    public void onAuthFailure(Failure failure) {
                        onAuthCall.onAuthLoading(false);

                        onAuthCall.onAuthFailure(failure);
                    }
                }
        );
    }

    @Override
    public void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        onAuthCall.onAuthLoading(true);
        authRepo.signUpWithEmailAndPassword(customAuthCredentials);
    }

    @Override
    public void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        onAuthCall.onAuthLoading(true);
        authRepo.signInWithEmailAndPassword(customAuthCredentials);
    }

    @Override
    public void signInWithGoogle(Activity activity) {
        onAuthCall.onAuthLoading(true);
        authRepo.signInWithGoogle(activity);
    }
}
