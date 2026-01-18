package com.dmy.foodplannerapp.presentation.auth.presenter;

import android.app.Activity;

import com.dmy.foodplannerapp.data.model.CustomAuthCredentials;

public interface AuthPresenter {
    void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials);

    void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials);

    void signInWithGoogle(Activity activity);
    
}
