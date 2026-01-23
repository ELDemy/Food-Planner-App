package com.dmy.foodplannerapp.presentation.splash.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.presentation.auth.view.AuthActivity;
import com.dmy.foodplannerapp.presentation.home.view.HomeActivity;
import com.dmy.foodplannerapp.presentation.splash.presenter.SplashPresenter;

public class SplashActivity extends AppCompatActivity {
    TextView appName;
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        appName = findViewById(R.id.tvAppName);
        splashPresenter = new SplashPresenter();
        checkIfUserIsLoggedIn();
    }

    private void checkIfUserIsLoggedIn() {
        if (splashPresenter.checkIfUserIsLoggedIn()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        appName.setAlpha(1f);

        ObjectAnimator bounceAnimator = ObjectAnimator.ofFloat(
                appName,
                "translationY",
                0f, -50f, 0f
        );

        bounceAnimator.setDuration(650);

        bounceAnimator.setRepeatCount(ValueAnimator.INFINITE);
        bounceAnimator.setRepeatMode(ValueAnimator.REVERSE);

        bounceAnimator.start();
    }
}