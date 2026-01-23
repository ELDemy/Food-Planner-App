package com.dmy.foodplannerapp.data.auth.repo;

import android.app.Activity;

import com.dmy.foodplannerapp.data.auth.remote.data_source.AuthRemoteDataSource;
import com.dmy.foodplannerapp.data.auth.remote.data_source.FirebaseAuthDataSource;
import com.dmy.foodplannerapp.data.auth.remote.model.CustomAuthCredentials;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepoImp implements AuthRepo {
    AuthRemoteDataSource remoteDataSource;

    public AuthRepoImp(MyCallBack<FirebaseUser> myCallBack) {
        remoteDataSource = new FirebaseAuthDataSource(myCallBack);
    }

    @Override
    public void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        remoteDataSource.signUpWithEmailAndPassword(customAuthCredentials);
    }

    @Override
    public void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        remoteDataSource.signInWithEmailAndPassword(customAuthCredentials);
    }

    @Override
    public void signInWithGoogle(Activity activity) {
        remoteDataSource.signInWithGoogle(activity);
    }

    @Override
    public void continueAsGuest() {
        remoteDataSource.continueAsGuest();
    }

    @Override
    public boolean checkIfUserIsLoggedIn() {
        return remoteDataSource.checkIfUserIsLoggedIn();
    }
}
