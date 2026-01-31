package com.dmy.foodplannerapp.presentation.splash.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.presentation.auth.view.AuthActivity;
import com.dmy.foodplannerapp.presentation.main_activity.view.MainActivity;
import com.dmy.foodplannerapp.presentation.splash.presenter.SplashPresenter;

public class SplashActivity extends AppCompatActivity implements SplashView {
    private static final String TAG = "SplashActivity";
    TextView appName;
    SplashPresenter splashPresenter;
    ObjectAnimator bounceAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        appName = findViewById(R.id.tvAppName);
        splashPresenter = new SplashPresenter(this, this);
        checkIfUserIsLoggedIn();
    }

    private void checkIfUserIsLoggedIn() {
        splashPresenter.checkIfUserIsLoggedIn();
    }

    @Override
    protected void onStart() {
        super.onStart();
        appName.setAlpha(1f);

        bounceAnimator = ObjectAnimator.ofFloat(
                appName,
                "translationY",
                0f, -50f, 0f
        );

        bounceAnimator.setDuration(650);

        bounceAnimator.setRepeatCount(ValueAnimator.INFINITE);
        bounceAnimator.setRepeatMode(ValueAnimator.REVERSE);

        bounceAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bounceAnimator.cancel();
    }

    @Override
    public void navigateToHomeScreen() {
        Log.i(TAG, "navigateToHomeScreen: ");
        appName.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }

    @Override
    public void navigateToAuthScreen() {
        appName.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}