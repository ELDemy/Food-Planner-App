package com.dmy.foodplannerapp.presentation.search.view.items_list;

import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.dto.FilterItem;

import java.util.List;

public interface ItemsListView {
    void onSuccess(List<? extends FilterItem> data);

    void onFailure(Failure failure);

    void onLoad(boolean isLoading);

}
