package com.dmy.foodplannerapp;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

// this class is to remove the night mode from the app
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
