package com.dmy.foodplannerapp.data.auth.remote.firebase_auth_service;

import android.util.Log;

import com.dmy.foodplannerapp.data.auth.remote.data_source.OnAuthCallBack;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthAnonymously {
    private final String TAG = "AuthAnonymously";

    private final FirebaseAuth authInstance;
    private final OnAuthCallBack onAuthCallBack;

    public AuthAnonymously(OnAuthCallBack onAuthCallBack) {
        this.onAuthCallBack = onAuthCallBack;
        authInstance = FirebaseAuth.getInstance();
    }

    public void signInAnonymously() {
        authInstance.signInAnonymously().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInAnonymously success");
                        FirebaseUser user = authInstance.getCurrentUser();
                        if (user != null) {
                            onAuthCallBack.onAuthSuccess(user);
                        } else {
                            handleFailure(task, "signInAnonymously");
                        }
                    } else {
                        handleFailure(task, "signInAnonymously");
                    }
                })
                .addOnFailureListener(e -> handleFailure(e, "signInAnonymously"));
    }

    private void handleFailure(Exception e, String tag) {
        onAuthCallBack.onAuthFailure(FailureHandler.handle(e, tag));
    }

    private void handleFailure(Task<AuthResult> task, String tag) {
        onAuthCallBack.onAuthFailure(FailureHandler.handle(task, tag));
    }

}
