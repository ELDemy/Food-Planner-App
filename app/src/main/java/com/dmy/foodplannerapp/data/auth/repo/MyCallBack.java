package com.dmy.foodplannerapp.data.auth.repo;

import com.dmy.foodplannerapp.data.failure.Failure;

public interface MyCallBack<T> {
    void onSuccess(T data);

    void onFailure(Failure failure);
}
