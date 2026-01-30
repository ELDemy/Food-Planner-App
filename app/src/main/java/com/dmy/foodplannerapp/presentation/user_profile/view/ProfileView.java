package com.dmy.foodplannerapp.presentation.user_profile.view;

public interface ProfileView {

    void onSync();

    void onSignOut();

    void onFailure(String message);
}
