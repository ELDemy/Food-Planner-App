package com.dmy.foodplannerapp.presentation.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.ArgumentSearchScreenModel;

public class MealsListScreenFragment extends Fragment {

    private static final String TAG = "MealsListScreenFragment";
    ArgumentSearchScreenModel arguments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        arguments = MealsListScreenFragmentArgs.fromBundle(getArguments()).getData();

        Log.i(TAG, "onViewCreated: " + arguments.getType() + arguments.getName());
    }
}