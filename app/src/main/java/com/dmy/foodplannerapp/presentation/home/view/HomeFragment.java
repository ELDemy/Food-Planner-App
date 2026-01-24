package com.dmy.foodplannerapp.presentation.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.meals.remote.MealsRemoteDataSourceImpl;
import com.dmy.foodplannerapp.presentation.reusable_components.CustomSnackBar;

public class HomeFragment extends Fragment {
    CardView featuredMeal;

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
        featuredMeal = view.findViewById(R.id.card_featuredContainer);
        featuredMeal.setOnClickListener(btnView -> {
            CustomSnackBar.showInfo(view, "Meal clicked");
            new MealsRemoteDataSourceImpl().getMealById(52772);
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mealProfileFragment);
        });
    }
}