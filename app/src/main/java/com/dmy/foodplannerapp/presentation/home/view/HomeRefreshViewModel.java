package com.dmy.foodplannerapp.presentation.home.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeRefreshViewModel extends ViewModel {
    private final MutableLiveData<Boolean> refreshTrigger = new MutableLiveData<>();

    public void requestRefresh() {
        refreshTrigger.setValue(true);
    }

    public LiveData<Boolean> getRefreshTrigger() {
        return refreshTrigger;
    }
}
