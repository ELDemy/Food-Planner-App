package com.dmy.foodplannerapp.data.auth.remote.data_source;


import android.app.Activity;

import com.dmy.foodplannerapp.data.auth.remote.firebase_auth_service.AuthWithEmailAndPassword;
import com.dmy.foodplannerapp.data.auth.remote.firebase_auth_service.AuthWithGoogle;
import com.dmy.foodplannerapp.data.model.CustomAuthCredentials;

public class AuthRemoteDataSourceImp implements AuthRemoteDataSource {
    private final String TAG = "RemoteDataSourceImp";

    private final AuthWithEmailAndPassword authWithEmailAndPassword;
    private final AuthWithGoogle authWithGoogle;

    public AuthRemoteDataSourceImp(OnAuthCallBack onAuthCallBack) {
        authWithEmailAndPassword = new AuthWithEmailAndPassword(onAuthCallBack);
        authWithGoogle = new AuthWithGoogle(onAuthCallBack);
    }

    public void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        authWithEmailAndPassword.signUpWithEmailAndPassword(customAuthCredentials);
    }

    public void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        authWithEmailAndPassword.signInWithEmailAndPassword(customAuthCredentials);
    }

    public void signInWithGoogle(Activity activity) {
        authWithGoogle.signIn(activity);
    }
}
