package com.dmy.foodplannerapp.data.auth.repo;

import android.app.Activity;

import com.dmy.foodplannerapp.data.auth.remote.model.CustomAuthCredentials;
import com.dmy.foodplannerapp.data.model.entity.User;

public interface AuthRepo {
    void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials);

    void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials);

    void signInWithGoogle(Activity activity);

    void continueAsGuest();

    void checkIfUserIsLoggedIn();

    void signOut();

    void getUserData(MyCallBack<User> callBack);

    void setUserData(User user, MyCallBack<Boolean> callBack);
}
