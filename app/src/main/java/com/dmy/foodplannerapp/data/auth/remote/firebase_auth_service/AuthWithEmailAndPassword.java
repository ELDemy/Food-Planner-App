package com.dmy.foodplannerapp.data.auth.remote.firebase_auth_service;

import android.util.Log;

import com.dmy.foodplannerapp.data.auth.remote.data_source.OnAuthCallBack;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.dmy.foodplannerapp.data.model.CustomAuthCredentials;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthWithEmailAndPassword {
    private final String TAG = "RemoteDataSourceImp";

    private final FirebaseAuth authInstance;
    private final OnAuthCallBack onAuthCallBack;

    public AuthWithEmailAndPassword(OnAuthCallBack onAuthCallBack) {
        this.onAuthCallBack = onAuthCallBack;
        authInstance = FirebaseAuth.getInstance();
    }

    public void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        Task<AuthResult> result = authInstance.createUserWithEmailAndPassword(customAuthCredentials.getEmail(), customAuthCredentials.getPassword());

        result.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = authInstance.getCurrentUser();
                        if (user != null) {
                            onAuthCallBack.onAuthSuccess(user);
                        } else {
                            handleFailure(task, "signUpWithEmailAndPassword");
                        }
                    } else {
                        handleFailure(task, "signUpWithEmailAndPassword");
                    }
                })
                .addOnFailureListener(e -> handleFailure(e, "signUpWithEmailAndPassword"));
    }


    public void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        authInstance.signInWithEmailAndPassword(customAuthCredentials.getEmail(), customAuthCredentials.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = authInstance.getCurrentUser();
                        if (user != null) {
                            onAuthCallBack.onAuthSuccess(user);
                        } else {
                            handleFailure(task, "signInWithEmailAndPassword");
                        }
                    } else {
                        handleFailure(task, "signInWithEmailAndPassword");
                    }
                })
                .addOnFailureListener(e -> handleFailure(e, "signInWithEmailAndPassword"));
    }

    private void handleFailure(Exception e, String tag) {
        onAuthCallBack.onAuthFailure(FailureHandler.handle(e, tag));
    }

    private void handleFailure(Task<AuthResult> task, String tag) {
        onAuthCallBack.onAuthFailure(FailureHandler.handle(task, tag));
    }
}
