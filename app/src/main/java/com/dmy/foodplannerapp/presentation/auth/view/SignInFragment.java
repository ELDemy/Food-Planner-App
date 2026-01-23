package com.dmy.foodplannerapp.presentation.auth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.auth.remote.model.CustomAuthCredentials;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.User;
import com.dmy.foodplannerapp.presentation.auth.presenter.AuthPresenter;
import com.dmy.foodplannerapp.presentation.auth.presenter.AuthPresenterImp;
import com.dmy.foodplannerapp.presentation.reusable_components.CustomSnackBar;
import com.dmy.foodplannerapp.presentation.reusable_components.TextFormField;

public class SignInFragment extends Fragment implements AuthView {
    TextView signUpText;
    TextFormField emailField;
    TextFormField passwordField;
    Button signInButton;
    AuthPresenter authPresenter;
    LinearLayout googleBtn;
    Button guestBtn;
    AuthCommunicator authCommunicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authCommunicator = (AuthCommunicator) getActivity();
        authPresenter = new AuthPresenterImp(this);

        signUpText = view.findViewById(R.id.text_signUp);
        emailField = view.findViewById(R.id.email_field);
        passwordField = view.findViewById(R.id.password_field);
        signInButton = view.findViewById(R.id.btn_sign_in);
        googleBtn = view.findViewById(R.id.btn_google);
        guestBtn = view.findViewById(R.id.btn_guest);

        signUpText.setOnClickListener((txtView) -> Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment));

        signInButton.setOnClickListener((btnView) -> {
            CustomAuthCredentials customAuthCredentials = getAuthCredentials();
            if (customAuthCredentials == null) {
                return;
            }
            authPresenter.signInWithEmailAndPassword(customAuthCredentials);
        });

        googleBtn.setOnClickListener((btnView) -> authPresenter.signInWithGoogle(getActivity()));

        guestBtn.setOnClickListener((btnView) -> authPresenter.continueAsGuest());
    }

    private CustomAuthCredentials getAuthCredentials() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (!validate()) {
            return null;
        }
        return new CustomAuthCredentials(email, password);
    }

    @Override
    public void onAuthSuccess(User user) {
        authCommunicator.hideLoading();
        authCommunicator.goToHomeScreen();
    }

    @Override
    public void onAuthFailure(Failure failure) {
        authCommunicator.hideLoading();
        CustomSnackBar.showFailure(getView(), failure.getMessage());
    }

    @Override
    public void onAuthLoading(boolean isLoading) {
        authCommunicator.showLoading();
    }

    private boolean validate() {
        boolean isValid = true;
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || email == null) {
            emailField.setError("Can't be empty");
            isValid = false;
        } else if (!email.contains("@") || !email.contains(".")) {
            emailField.setError("Invalid email");
            isValid = false;
        }

        if (password.isEmpty() || password == null) {
            passwordField.setError("Can't be empty");
            isValid = false;
        }

        return isValid;
    }


}