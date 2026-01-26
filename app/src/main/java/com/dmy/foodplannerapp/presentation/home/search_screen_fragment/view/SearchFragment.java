package com.dmy.foodplannerapp.presentation.home.search_screen_fragment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.ArgumentSearchScreenModel;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.databinding.FragmentMealsListScreenBinding;
import com.dmy.foodplannerapp.presentation.home.search_screen_fragment.presenter.SearchPresenter;
import com.dmy.foodplannerapp.presentation.home.search_screen_fragment.presenter.SearchPresenterImpl;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView {

    private static final String TAG = "SearchFragment";
    ArgumentSearchScreenModel arguments;
    SearchPresenter searchPresenter;
    FragmentMealsListScreenBinding binding;
    RecyclerView recyclerView;
    SearchListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMealsListScreenBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals_list_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arguments = SearchFragmentArgs.fromBundle(getArguments()).getData();
        Log.i(TAG, "onViewCreated: " + arguments.getType() + arguments.getName());

        recyclerView = view.findViewById(R.id.recycler_meals);
        adapter = new SearchListAdapter(requireContext());
        recyclerView.setAdapter(adapter);

        searchPresenter = new SearchPresenterImpl(this);
        searchPresenter.searchMeals(arguments);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onSuccess(List<MealEntity> data) {
        Log.i(TAG, "Loaded Meals: " + data.size());
        adapter.setMeals(data);
    }

    @Override
    public void onFailure(Failure failure) {

    }
}