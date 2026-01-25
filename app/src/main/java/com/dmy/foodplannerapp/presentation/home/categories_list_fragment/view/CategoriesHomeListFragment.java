package com.dmy.foodplannerapp.presentation.home.categories_list_fragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.CategoryEntity;
import com.dmy.foodplannerapp.presentation.home.categories_list_fragment.presenter.CategoriesListPresenter;
import com.dmy.foodplannerapp.presentation.home.categories_list_fragment.presenter.CategoriesListPresenterImpl;

import java.util.List;

public class CategoriesHomeListFragment extends Fragment implements CategoriesListView {
    RecyclerView rvCategories;
    CategoriesHomeListRecyclerAdapter adapter;
    TextView errTxt;
    LottieAnimationView loading;

    CategoriesListPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_categories_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCategories = view.findViewById(R.id.rv_categories);
        errTxt = view.findViewById(R.id.tv_error);
        loading = view.findViewById(R.id.loading);

        presenter = new CategoriesListPresenterImpl(this);

        adapter = new CategoriesHomeListRecyclerAdapter(requireContext());
        rvCategories.setAdapter(adapter);

        presenter.getCategories();
    }

    @Override
    public void onLoading(boolean isLoading) {
        loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        rvCategories.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onFailure(String message) {
        errTxt.setVisibility(View.VISIBLE);
        errTxt.setText(message);
        rvCategories.setVisibility(View.GONE);
    }

    @Override
    public void updateCategories(List<CategoryEntity> categories) {
        adapter.updateList(categories);
    }
}