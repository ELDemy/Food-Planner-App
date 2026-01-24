package com.dmy.foodplannerapp.data.failure;


import android.util.Log;

import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import retrofit2.Response;

public final class FailureHandler {
    private static final String TAG = "FailureMapper";

    private FailureHandler() {
    }


    public static Failure handle(Response response, String tag) {
        int code = response.code();
        Log.w(tag, "error in response with code " + code + " message " + response.message() + "\nresponse is: " + response);

        String message = response.message();
        if (!message.isEmpty()) {
            return new Failure(message);
        }

        return new Failure("Something went wrong!!");
    }

    public static Failure handle(Throwable t, String tag) {
        t.printStackTrace();
        Log.w(tag, t.getMessage(), t);

        String message = t.getMessage();
        if (message != null && !message.isEmpty()) {
            return new Failure(tag + ": " + message);
        }

        return new Failure("Unexpected Error!!");
    }

    public static Failure handle(Task<AuthResult> task, String tag) {
        Exception e = task.getException();
        if (e != null) {
            log(e, tag);
            return handle(e, tag);
        }
        return new Failure("Unexpected authentication error");
    }

    public static Failure handle(Exception e, String tag) {
        log(e, tag);

        if (e instanceof GetCredentialException) {
            return new Failure(
                    "Google sign-in failed: "
                            + ((GetCredentialException) e).getErrorMessage()
            );
        }

        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            return new Failure("Invalid email or password");
        }

        if (e instanceof FirebaseAuthUserCollisionException) {
            return new Failure("This email is already registered");
        }

        if (e instanceof FirebaseAuthException) {
            return new Failure("Authentication error: " + e.getMessage());
        }

        if (e.getMessage() != null && !e.getMessage().isEmpty()) {
            return new Failure(tag + ": " + e.getMessage());
        }

        return new Failure("Unexpected authentication error");
    }

    private static void log(Exception e, String tag) {
        e.printStackTrace();
        Log.w(TAG, tag, e);
    }

}

