package com.dmy.foodplannerapp.presentation.auth.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.presentation.main_activity.view.MainActivity;

public class AuthActivity extends AppCompatActivity implements AuthCommunicator {
    ProgressBar progressBar;
    View blockingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        progressBar = findViewById(R.id.authProgressBar);
        blockingView = findViewById(R.id.blockingView);
    }


    @Override
    public void showLoading() {
        blockingView.setVisibility(VISIBLE);
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        blockingView.setVisibility(GONE);
        progressBar.setVisibility(GONE);
    }

    @Override
    public void goToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}