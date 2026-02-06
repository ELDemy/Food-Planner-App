package com.dmy.foodplannerapp.presentation.search.view;

import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;

import java.util.List;

public interface SearchView {
    void onLoad();

    void onSuccess(List<SearchedMealResponse> data);

    void onFailure(Failure failure);

}
