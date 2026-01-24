package com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.presenter.SuggestedMealsPresenter;
import com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.presenter.SuggestedMealsPresenterImpl;

import java.util.List;

public class SuggestedMealsFragment extends Fragment implements SuggestedMealsView {
    SuggestedMealsPresenter suggestedMealsPresenter;

    RecyclerView rvSuggestedMeals;
    SuggestedMealsAdapter suggestedMealsAdapter;

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
        suggestedMealsAdapter = new SuggestedMealsAdapter(requireContext());
        rvSuggestedMeals.setAdapter(suggestedMealsAdapter);
        suggestedMealsPresenter.getSuggestedMeals();
    }


    @Override
    public void updateSuggestedMeals(List<MealEntity> meals) {
        suggestedMealsAdapter.updateList(meals);
    }

    @Override
    public void onFailure(String message) {

    }
}