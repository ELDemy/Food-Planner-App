package com.dmy.foodplannerapp.presentation.auth.view;

import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.entity.User;

public interface AuthView {
    void onAuthSuccess(User user);

    void onAuthFailure(Failure failure);

    void onAuthLoading(boolean isLoading);
}
