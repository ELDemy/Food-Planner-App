package com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.view;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.presenter.SuggestedMealsPresenter;
import com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.presenter.SuggestedMealsPresenterImpl;
import com.dmy.foodplannerapp.presentation.home.view.HomeRefreshViewModel;

import java.util.List;

public class SuggestedMealsFragment extends Fragment implements SuggestedMealsView {
    private static final String TAG = "SuggestedMealsFragment";
    private static List<MealEntity> dataLoaded;
    SuggestedMealsPresenter suggestedMealsPresenter;
    LottieAnimationView loadingAnimation;
    RecyclerView rvSuggestedMeals;
    SuggestedMealsAdapter suggestedMealsAdapter;
    HomeRefreshViewModel sharedViewModel;
    TextView errorText;

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
        errorText = view.findViewById(R.id.tv_error);

        suggestedMealsAdapter = new SuggestedMealsAdapter(requireContext());
        rvSuggestedMeals.setAdapter(suggestedMealsAdapter);

        if (dataLoaded == null || dataLoaded.isEmpty()) {
            suggestedMealsPresenter.getSuggestedMeals();
        }

        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomeRefreshViewModel.class);

        sharedViewModel.getRefreshTrigger().observe(getViewLifecycleOwner(), shouldRefresh -> {
            if (shouldRefresh != null && shouldRefresh) {
                suggestedMealsPresenter.getSuggestedMeals();
            }
        });
    }

    @Override
    public void updateSuggestedMeals(List<MealEntity> meals) {
        if (meals.isEmpty()) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("No meals found");
            return;
        } else {
            errorText.setVisibility(GONE);
            dataLoaded = meals;
        }
        suggestedMealsAdapter.updateList(meals);
    }

    @Override
    public void onFailure(String message) {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(message);
    }

    @Override
    public void onLoad(boolean isLoading) {
        errorText.setVisibility(GONE);
        loadingAnimation.setVisibility(isLoading ? View.VISIBLE : GONE);
        rvSuggestedMeals.setVisibility(isLoading ? GONE : View.VISIBLE);
    }
}