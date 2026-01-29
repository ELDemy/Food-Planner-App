package com.dmy.foodplannerapp.presentation.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dmy.foodplannerapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class HomeFragment extends Fragment {

    private TextInputLayout searchText;
    private HomeRefreshViewModel sharedViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomeRefreshViewModel.class);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        setUpSearchTextField(view);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            sharedViewModel.requestRefresh();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    void setUpSearchTextField(View view) {
        searchText = view.findViewById(R.id.txtField_searchLayout);
        View.OnClickListener searchClickListener = v -> {
            HomeFragmentDirections.ActionHomeFragmentToMealsListScreenFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealsListScreenFragment(null);
            Navigation.findNavController(view).navigate(action);
        };
        searchText.setOnClickListener(searchClickListener);
        searchText.getEditText().setOnClickListener(searchClickListener);

        searchText.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchText.getEditText().clearFocus();
                HomeFragmentDirections.ActionHomeFragmentToMealsListScreenFragment action =
                        HomeFragmentDirections.actionHomeFragmentToMealsListScreenFragment(null);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}