package com.dmy.foodplannerapp.presentation.auth.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.User;
import com.dmy.foodplannerapp.presentation.auth.presenter.AuthPresenter;
import com.dmy.foodplannerapp.presentation.auth.presenter.AuthPresenterImp;

public class AuthActivity extends AppCompatActivity implements AuthView {
    final String TAG = "AuthActivity";

    AuthPresenter authPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authPresenter = new AuthPresenterImp(this);
    }


    @Override
    public void onAuthSuccess(User user) {
        Toast.makeText(AuthActivity.this, "Welcome " + user.getEmail(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthFailure(Failure failure) {
        Toast.makeText(AuthActivity.this, failure.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthLoading(boolean isLoading) {

    }
}