package com.dmy.foodplannerapp.data.auth.remote.data_source;

import android.app.Activity;

import com.dmy.foodplannerapp.data.model.CustomAuthCredentials;

public interface AuthRemoteDataSource {
    void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials);

    void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials);

    void signInWithGoogle(Activity activity);

    void continueAsGuest();
}
