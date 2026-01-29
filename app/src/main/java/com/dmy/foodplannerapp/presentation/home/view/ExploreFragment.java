package com.dmy.foodplannerapp.presentation.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;

public class ExploreFragment extends Fragment {
    CardView countryCardView;
    CardView ingrediantCardView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countryCardView = view.findViewById(R.id.card_exploreCountry);
        ingrediantCardView = view.findViewById(R.id.card_exploreIngredients);

        countryCardView.setOnClickListener(view1 -> {
            HomeFragmentDirections.ActionHomeFragmentToItemsScreenFragment2 action
                    = HomeFragmentDirections.actionHomeFragmentToItemsScreenFragment2(SearchModel.SearchType.COUNTRY);
            Navigation.findNavController(view).navigate(action);
        });


        ingrediantCardView.setOnClickListener(view1 -> {
            HomeFragmentDirections.ActionHomeFragmentToItemsScreenFragment2 action
                    = HomeFragmentDirections.actionHomeFragmentToItemsScreenFragment2(SearchModel.SearchType.INGREDIENT);
            Navigation.findNavController(view).navigate(action);
        });
    }
}