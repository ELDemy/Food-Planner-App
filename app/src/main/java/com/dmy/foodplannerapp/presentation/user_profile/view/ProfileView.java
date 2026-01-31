package com.dmy.foodplannerapp.presentation.user_profile.view;

import com.dmy.foodplannerapp.data.model.entity.User;

public interface ProfileView {

    void onSync();

    void onSignOut();

    void onFailure(String message);

    void updateUserData(User user);

    void updateUserStatue(Boolean isLoggedIn);
}
