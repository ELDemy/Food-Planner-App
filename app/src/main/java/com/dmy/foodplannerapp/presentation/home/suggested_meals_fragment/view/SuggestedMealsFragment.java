package com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.presenter.SuggestedMealsPresenter;
import com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.presenter.SuggestedMealsPresenterImpl;
import com.dmy.foodplannerapp.presentation.home.view.HomeRefreshViewModel;

import java.util.List;

public class SuggestedMealsFragment extends Fragment implements SuggestedMealsView {
    SuggestedMealsPresenter suggestedMealsPresenter;
    LottieAnimationView loadingAnimation;
    RecyclerView rvSuggestedMeals;
    SuggestedMealsAdapter suggestedMealsAdapter;
    HomeRefreshViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggested_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        suggestedMealsPresenter = new SuggestedMealsPresenterImpl(requireContext(), this);
        rvSuggestedMeals = view.findViewById(R.id.rv_meals);
        loadingAnimation = view.findViewById(R.id.loading);

        suggestedMealsAdapter = new SuggestedMealsAdapter(requireContext());
        rvSuggestedMeals.setAdapter(suggestedMealsAdapter);
        suggestedMealsPresenter.getSuggestedMeals();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomeRefreshViewModel.class);

        sharedViewModel.getRefreshTrigger().observe(getViewLifecycleOwner(), shouldRefresh -> {
            if (shouldRefresh != null && shouldRefresh) {
                suggestedMealsPresenter.getSuggestedMeals();
            }
        });
    }


    @Override
    public void updateSuggestedMeals(List<MealEntity> meals) {
        suggestedMealsAdapter.updateList(meals);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onLoad(boolean isLoading) {
        Log.i("ONLOAD", "onLoad: " + isLoading);
        loadingAnimation.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        rvSuggestedMeals.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }
}