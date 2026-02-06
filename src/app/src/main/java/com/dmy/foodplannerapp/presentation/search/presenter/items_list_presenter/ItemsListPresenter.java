package com.dmy.foodplannerapp.presentation.search.presenter.items_list_presenter;


import com.dmy.foodplannerapp.data.model.entity.SearchModel;

public interface ItemsListPresenter {
    void loadItems(SearchModel.SearchType itemType);

    void filter(String query);

}

