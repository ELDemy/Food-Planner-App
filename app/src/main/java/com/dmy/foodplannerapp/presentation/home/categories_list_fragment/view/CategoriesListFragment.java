package com.dmy.foodplannerapp.presentation.home.categories_list_fragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.CategoryEntity;

import java.util.List;

public class CategoriesListFragment extends Fragment implements CategoriesListView {
    RecyclerView rvCategories;
    CategoriesListRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCategories = view.findViewById(R.id.rv_categories);
        adapter = new CategoriesListRecyclerAdapter(requireContext());
        rvCategories.setAdapter(adapter);
    }

    @Override
    public void onLoading(boolean isLoading) {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void updateCategories(List<CategoryEntity> categories) {
        adapter.updateList(categories);
    }
}