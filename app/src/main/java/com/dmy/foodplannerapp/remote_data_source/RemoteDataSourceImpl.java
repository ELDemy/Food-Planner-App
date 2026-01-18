package com.dmy.foodplannerapp.remote_data_source;


import android.app.Activity;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.dmy.foodplannerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executors;

public class RemoteDataSourceImpl {
    public static final int RC_GOOGLE_SIGN_IN = 100;
    final String TAG = "RemoteDataSourceImpl";
    FirebaseAuth mAuth;

    public void signUpWithEmailAndPassword(String email, String password) {
        email = "mahmoud@gmail.com";
        password = "123456";

        mAuth = FirebaseAuth.getInstance();

        var result = mAuth.createUserWithEmailAndPassword(email, password);
        result.addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                }
        );
    }

    public void signInWithEmailAndPassword(String email, String password) {
        email = "mahmoud@gmail.com";
        password = "123456";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });

    }

    public void startGoogleSignIn(Activity activity) {
        Toast.makeText(activity, "startGoogleSignIn", Toast.LENGTH_LONG).show();
        // 1. Correct Initialization)
        // 1. Correct Initialization
        CredentialManager credentialManager = CredentialManager.create(activity);

        // 2. Build the Google Option
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(activity.getString(R.string.default_web_client_id))
                .setAutoSelectEnabled(true)
                .build();

        // 3. Build the Request
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        // 4. The 5-argument call required for Java
        credentialManager.getCredentialAsync(
                activity,
                request,
                new CancellationSignal(), // Required argument 3
                Executors.newSingleThreadExecutor(), // Required argument 4
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() { // Required argument 5
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        Log.d(TAG, "Credential received successfully");
                        Credential credential = result.getCredential();

                        if (credential instanceof CustomCredential &&
                                credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                            // Extract the Google ID Token
                            GoogleIdTokenCredential googleIdTokenCredential =
                                    GoogleIdTokenCredential.createFrom(credential.getData());
                            String idToken = googleIdTokenCredential.getIdToken();

                            // Use the token to authenticate with Firebase
                            AuthCredential firebaseAuthCredential = GoogleAuthProvider.getCredential(idToken, null);

                            mAuth = FirebaseAuth.getInstance();
                            mAuth.signInWithCredential(firebaseAuthCredential)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Firebase Login Success: " + mAuth.getCurrentUser().getEmail());
                                        } else {
                                            Log.e(TAG, "Firebase Login Failed", task.getException());
                                        }
                                    });

                        }
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Credential Manager Error: " + e.getErrorMessage());
                    }
                }
        );
    }

    private void handleSignInResult(GetCredentialResponse result) {
        // Implementation for Firebase Auth with Google Credential goes here
    }

}
