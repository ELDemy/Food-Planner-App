package com.dmy.foodplannerapp.data.auth.repo;

import android.app.Activity;

import com.dmy.foodplannerapp.data.model.CustomAuthCredentials;

public interface AuthRepo {
    void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials);

    void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials);

    void signInWithGoogle(Activity activity);
}
