package com.dmy.foodplannerapp;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

// this class is to remove the night mode from the app
public class MyApp extends Application {

    private static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setupRxJavaErrorHandler();
    }

    private void setupRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }

            // Log the error but don't crash the app
            if (e instanceof java.net.UnknownHostException) {
                Log.w(TAG, "Network error (no internet): " + e.getMessage());
                return;
            }
            if (e instanceof java.io.IOException) {
                Log.w(TAG, "Network/IO error: " + e.getMessage());
                return;
            }
            if (e instanceof InterruptedException) {
                Log.w(TAG, "Thread interrupted: " + e.getMessage());
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }

            Log.w(TAG, "Undeliverable exception received: " + e.getMessage(), e);
        });
    }
}
