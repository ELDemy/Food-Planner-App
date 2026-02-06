package com.dmy.foodplannerapp.utils;

import android.graphics.Color;
import android.view.View;

import com.dmy.foodplannerapp.R;
import com.google.android.material.snackbar.Snackbar;

public class CustomSnackBar {

    public static void showSuccess(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#2E7D32")); // Material Success Green
        snackbar.setTextColor(Color.WHITE);
        anchorToBottomNav(view, snackbar);
        snackbar.show();
    }

    public static void showFailure(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#D32F2F")); // Material Error Red
        snackbar.setTextColor(Color.WHITE);
        anchorToBottomNav(view, snackbar);
        snackbar.show();
    }

    public static void showInfo(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#1976D2")); // Material Info Blue
        snackbar.setTextColor(Color.WHITE);
        anchorToBottomNav(view, snackbar);
        snackbar.show();
    }

    private static void anchorToBottomNav(View view, Snackbar snackbar) {
        View rootView = view.getRootView();
        View bottomNav = rootView.findViewById(R.id.main_navigator);
        if (bottomNav != null && bottomNav.getVisibility() == View.VISIBLE) {
            snackbar.setAnchorView(bottomNav);
        }
    }
}