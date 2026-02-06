package com.dmy.foodplannerapp.data.auth.repo;

import android.app.Activity;

import com.dmy.foodplannerapp.data.auth.remote.data_source.AuthRemoteDataSource;
import com.dmy.foodplannerapp.data.auth.remote.data_source.FirebaseAuthDataSource;
import com.dmy.foodplannerapp.data.auth.remote.model.CustomAuthCredentials;
import com.dmy.foodplannerapp.data.meals.remote.firestore.FirestoreRemoteDataSource;
import com.dmy.foodplannerapp.data.model.entity.User;

public class AuthRepoImp implements AuthRepo {
    AuthRemoteDataSource remoteDataSource;
    FirestoreRemoteDataSource firestoreRemoteDataSource;

    public AuthRepoImp(MyCallBack myCallBack) {
        remoteDataSource = new FirebaseAuthDataSource(myCallBack);
        firestoreRemoteDataSource = new FirestoreRemoteDataSource();
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
    public void checkIfUserIsLoggedIn() {
        remoteDataSource.checkIfUserIsLoggedIn();
    }

    @Override
    public void signOut() {
        remoteDataSource.signOut();
    }

    @Override
    public void getUserData(MyCallBack<User> callBack) {
        firestoreRemoteDataSource.getUserData(callBack);
    }

    @Override
    public void setUserData(User user, MyCallBack<Boolean> callBack) {
        firestoreRemoteDataSource.setUserData(user, callBack);
    }
}
