package com.dmy.foodplannerapp.data.auth.repo;

import android.app.Activity;

import com.dmy.foodplannerapp.data.auth.remote.data_source.AuthRemoteDataSource;
import com.dmy.foodplannerapp.data.auth.remote.data_source.AuthRemoteDataSourceImp;
import com.dmy.foodplannerapp.data.auth.remote.data_source.OnAuthCallBack;
import com.dmy.foodplannerapp.data.model.CustomAuthCredentials;

public class AuthRepoImp implements AuthRepo {
    AuthRemoteDataSource remoteDataSource;

    public AuthRepoImp(OnAuthCallBack onAuthCallBack) {
        remoteDataSource = new AuthRemoteDataSourceImp(onAuthCallBack);
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
}
