package com.dmy.foodplannerapp.presentation.user_profile.prsenter;

import android.content.Context;

import com.dmy.foodplannerapp.data.auth.repo.AuthRepo;
import com.dmy.foodplannerapp.data.auth.repo.AuthRepoImp;
import com.dmy.foodplannerapp.data.auth.repo.MyCallBack;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepo;
import com.dmy.foodplannerapp.data.meals.repo.MealsRepoImpl;
import com.dmy.foodplannerapp.data.model.entity.User;
import com.dmy.foodplannerapp.presentation.user_profile.view.ProfileView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfilePresenter {

    private static final String TAG = "ProfilePresenter";
    private final MealsRepo mealsRepo;
    private final ProfileView view;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private AuthRepo authRepo;

    public ProfilePresenter(Context context, ProfileView view) {
        this.view = view;
        mealsRepo = new MealsRepoImpl(context);
        initRepo();
    }

    private void initRepo() {
        authRepo = new AuthRepoImp(new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                view.onSignOut();
            }

            @Override
            public void onFailure(Failure failure) {
                view.onFailure(failure.getMessage());
            }
        });
    }

    public void signOut() {
        authRepo.signOut();
    }

    public void sync() {
        disposables.add(
                mealsRepo.syncAll().subscribe(
                        () -> view.onSync(),
                        error -> view.onFailure(error.getMessage())
                )
        );
    }

    public void getUserData() {
        authRepo.getUserData(new MyCallBack<>() {
            @Override
            public void onSuccess(User data) {
                view.updateUserData(data);
            }

            @Override
            public void onFailure(Failure failure) {
                view.updateUserData(new User("Guest", ""));
                view.onFailure(failure.getMessage());
            }
        });
    }

    public void dispose() {
        disposables.clear();
    }
}
