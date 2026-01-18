package com.dmy.foodplannerapp.data.auth.remote.firebase_auth_service;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.app.Activity;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.auth.remote.data_source.OnAuthCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.failure.FailureHandler;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthWithGoogle {
    private final String TAG = "RemoteDataSourceImp";

    private final OnAuthCallBack onAuthCallBack;
    private final FirebaseAuth authInstance;


    public AuthWithGoogle(OnAuthCallBack onAuthCallBack) {
        this.onAuthCallBack = onAuthCallBack;
        authInstance = FirebaseAuth.getInstance();
    }

    public void signIn(Activity activity) {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setServerClientId(activity.getString(R.string.default_web_client_id))
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        CredentialManager credentialManager = CredentialManager.create(activity);

        credentialManager.getCredentialAsync(
                activity,
                request,
                new CancellationSignal(), // Required argument 3
                ContextCompat.getMainExecutor(activity), // Required argument 4
                new CredentialManagerCallback<>() { // Required argument 5
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        Log.d(TAG, "Credential received successfully");
                        Credential credential = result.getCredential();

                        if (credential instanceof CustomCredential &&
                                credential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                            // Extract the Google ID Token
                            GoogleIdTokenCredential googleIdTokenCredential =
                                    GoogleIdTokenCredential.createFrom(credential.getData());
                            String idToken = googleIdTokenCredential.getIdToken();

                            // Use the token to authenticate with Firebase
                            AuthCredential firebaseAuthCredential = GoogleAuthProvider.getCredential(idToken, null);
                            handleSignIn(firebaseAuthCredential);

                        } else {
                            onAuthCallBack.onAuthFailure(
                                    new Failure("Invalid credential type")
                            );
                        }
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        handleFailure(e);
                    }
                }
        );
    }

    private void handleSignIn(AuthCredential firebaseAuthCredential) {

        authInstance.signInWithCredential(firebaseAuthCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Firebase Login Success: " + authInstance.getCurrentUser().getEmail());

                        FirebaseUser user = authInstance.getCurrentUser();
                        if (user != null) {
                            onAuthCallBack.onAuthSuccess(user);
                        } else {
                            handleFailure(task);
                        }
                    } else {
                        handleFailure(task);
                    }
                })
                .addOnFailureListener(this::handleFailure);
    }

    private void handleFailure(Exception e) {
        onAuthCallBack.onAuthFailure(FailureHandler.handle(e, "signInWithGoogle"));
    }

    private void handleFailure(Task<AuthResult> task) {
        onAuthCallBack.onAuthFailure(FailureHandler.handle(task, "Sign in failed"));
    }
}
