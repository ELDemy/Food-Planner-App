package com.dmy.foodplannerapp.presentation.reusable_components;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class CustomSnackBar {
    public static void show(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showFailure(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setMinimumHeight(50);
        snackbar.show();
    }

    public static void show(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }
}
