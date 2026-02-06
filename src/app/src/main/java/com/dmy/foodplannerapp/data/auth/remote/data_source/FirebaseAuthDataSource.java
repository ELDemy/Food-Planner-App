package com.dmy.foodplannerapp.data.auth.remote.data_source;


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
import com.dmy.foodplannerapp.data.auth.remote.model.CustomAuthCredentials;
import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseAuthDataSource implements AuthRemoteDataSource {
    private final String TAG = "FirebaseAuth";

    private final FirebaseAuth authInstance;
    private final MyCallBack myCallBack;

    public FirebaseAuthDataSource(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        authInstance = FirebaseAuth.getInstance();
    }

    public void signUpWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        Task<AuthResult> result = authInstance.createUserWithEmailAndPassword(customAuthCredentials.getEmail(), customAuthCredentials.getPassword());

        result.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = authInstance.getCurrentUser();
                        if (user != null) {
                            setUsername(user, customAuthCredentials.getName());
                            myCallBack.onSuccess(user);
                        } else {
                            handleFailure(task, "signUpWithEmailAndPassword");
                        }
                    } else {
                        handleFailure(task, "signUpWithEmailAndPassword");
                    }
                })
                .addOnFailureListener(e -> handleFailure(e, "signUpWithEmailAndPassword"));
    }

    private void setUsername(FirebaseUser user, String username) {
        if (user == null) return;

        UserProfileChangeRequest profileUpdates =
                new UserProfileChangeRequest.Builder().setDisplayName(username).build();

        user.updateProfile(profileUpdates)
                .addOnSuccessListener(unused ->
                        Log.i("AUTH", "Username set successfully"))
                .addOnFailureListener(e ->
                        Log.e("AUTH", "Failed to set username", e));
    }

    public void signInWithEmailAndPassword(CustomAuthCredentials customAuthCredentials) {
        authInstance.signInWithEmailAndPassword(customAuthCredentials.getEmail(), customAuthCredentials.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = authInstance.getCurrentUser();
                        if (user != null) {
                            myCallBack.onSuccess(user);
                        } else {
                            handleFailure(task, "signInWithEmailAndPassword");
                        }
                    } else {
                        handleFailure(task, "signInWithEmailAndPassword");
                    }
                })
                .addOnFailureListener(e -> handleFailure(e, "signInWithEmailAndPassword"));
    }

    public void signInWithGoogle(Activity activity) {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setServerClientId(activity.getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
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
                            myCallBack.onFailure(
                                    new Failure("Invalid credential type")
                            );
                        }
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        handleFailure(e, "signInWithGoogle");
                    }
                }
        );
    }


    private void handleSignIn(AuthCredential firebaseAuthCredential) {
        authInstance.signInWithCredential(firebaseAuthCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = authInstance.getCurrentUser();

                        if (user != null) {
                            Log.d(TAG, "Firebase Login Success: " + user.getEmail());
                            myCallBack.onSuccess(user);
                        } else {
                            handleFailure(task, "signInWithGoogle");
                        }
                    } else {
                        handleFailure(task, "signInWithGoogle");
                    }
                })
                .addOnFailureListener((e) -> handleFailure(e, "signInWithGoogle"));
    }

    public void signInAnonymously() {
        authInstance.signInAnonymously().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInAnonymously success");
                        FirebaseUser user = authInstance.getCurrentUser();
                        if (user != null) {
                            myCallBack.onSuccess(user);
                        } else {
                            handleFailure(task, "signInAnonymously");
                        }
                    } else {
                        handleFailure(task, "signInAnonymously");
                    }
                })
                .addOnFailureListener(e -> handleFailure(e, "signInAnonymously"));
    }

    @Override
    public void continueAsGuest() {
        signInAnonymously();
    }

    @Override
    public void checkIfUserIsLoggedIn() {
        FirebaseUser user = authInstance.getCurrentUser();

        if (user == null || user.isAnonymous()) {
            myCallBack.onFailure(null);
        } else {
            myCallBack.onSuccess(true);
        }
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        myCallBack.onSuccess(null);
    }


    private void handleFailure(Exception e, String tag) {
        myCallBack.onFailure(FailureHandler.handle(e, tag));
    }

    private void handleFailure(Task<AuthResult> task, String tag) {
        myCallBack.onFailure(FailureHandler.handle(task, tag));
    }
}
