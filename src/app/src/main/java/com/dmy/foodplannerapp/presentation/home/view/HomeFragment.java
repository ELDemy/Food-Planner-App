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

import com.airbnb.lottie.LottieAnimationView;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.utils.NetworkObserver;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HomeFragment extends Fragment {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private TextInputLayout searchText;
    private HomeRefreshViewModel sharedViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    private View fragmentMealOfTheDay;
    private View fragmentSuggestedMeals;
    private View fragmentCategoriesList;
    private View fragmentContainerView4;
    private LottieAnimationView lottieOffline;

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

        fragmentMealOfTheDay = view.findViewById(R.id.fragment_mealOfTheDay);
        fragmentSuggestedMeals = view.findViewById(R.id.fragment_suggestedMeals);
        fragmentCategoriesList = view.findViewById(R.id.fragment_categoriesList);
        fragmentContainerView4 = view.findViewById(R.id.fragmentContainerView4);
        lottieOffline = view.findViewById(R.id.lottie_offline);

        setUpSearchTextField(view);
        setupNetworkMonitoring();

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

    private void setupNetworkMonitoring() {
        disposables.add(
                NetworkObserver.getInstance(requireContext())
                        .observeConnection()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isConnected -> {
                            updateUIForNetworkState(isConnected);
                            sharedViewModel.requestRefresh();
                        })
        );
    }

    private void updateUIForNetworkState(Boolean isOnline) {
        if (isOnline) {
            fragmentSuggestedMeals.setVisibility(View.VISIBLE);
            fragmentCategoriesList.setVisibility(View.VISIBLE);
            fragmentContainerView4.setVisibility(View.VISIBLE);

            lottieOffline.setVisibility(View.GONE);
        } else {
            fragmentSuggestedMeals.setVisibility(View.GONE);
            fragmentCategoriesList.setVisibility(View.GONE);
            fragmentContainerView4.setVisibility(View.GONE);

            lottieOffline.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }
}