package com.dmy.foodplannerapp.data.auth.remote.data_source;

import com.dmy.foodplannerapp.data.failure.Failure;
import com.google.firebase.auth.FirebaseUser;

public interface OnAuthCallBack {
    void onAuthSuccess(FirebaseUser user);

    void onAuthFailure(Failure failure);
}
