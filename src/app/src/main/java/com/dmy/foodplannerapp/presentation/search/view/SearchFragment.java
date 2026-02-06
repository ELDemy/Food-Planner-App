package com.dmy.foodplannerapp.presentation.search.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.databinding.FragmentSearchMealsScreenBinding;
import com.dmy.foodplannerapp.presentation.search.presenter.SearchPresenter;
import com.dmy.foodplannerapp.presentation.search.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView {

    private static final String TAG = "SearchFragment";

    private final List<SearchModel> activeFilters = new ArrayList<>();
    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    SearchPresenter searchPresenter;
    FragmentSearchMealsScreenBinding binding;
    RecyclerView recyclerView;
    SearchListAdapter adapter;
    CardView backBtn;
    TextInputLayout searchText;
    LottieAnimationView loading;
    TextView errorText;
    private Runnable searchRunnable;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchMealsScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initViews();
        setupSearchText();

        setupFilterTypeChips();
        restoreFiltersFromNavigation();

        searchPresenter = new SearchPresenterImpl(this, requireContext());

        if (getArguments() != null) {
            SearchFragmentArgs args = SearchFragmentArgs.fromBundle(getArguments());
            SearchModel initialFilter = args.getData();
            if (initialFilter != null) {
                addFilter(initialFilter);
            } else {
                applyFilters();
            }
        } else {
            applyFilters();
        }
    }

    void initViews() {
        searchText = binding.txtFieldSearchLayout;
        backBtn = binding.btnBackContainer;
        backBtn.setOnClickListener(v -> NavHostFragment
                .findNavController(SearchFragment.this)
                .popBackStack(R.id.homeFragment, false));
        errorText = binding.tvListError;
        loading = binding.loading;
        recyclerView = binding.recyclerMeals;
        adapter = new SearchListAdapter(requireContext());
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchText() {
        searchText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                final String searchQuery = charSequence.toString().trim();
                searchRunnable = () -> {
                    Log.i(TAG, "onTextChanged: " + searchQuery);
                    filterByName(searchQuery);
                };

                searchHandler.postDelayed(searchRunnable, 300);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filterByName(String text) {
        if (text == null || text.isEmpty()) {
            applyFilters();
        } else {
            searchPresenter.searchMeals(text, activeFilters);
        }
    }

    private void setupFilterTypeChips() {
        binding.chipCategory.setOnClickListener(v -> navigateToItemsScreen(SearchModel.SearchType.CATEGORY));
        binding.chipIngredient.setOnClickListener(v -> navigateToItemsScreen(SearchModel.SearchType.INGREDIENT));
        binding.chipCountry.setOnClickListener(v -> navigateToItemsScreen(SearchModel.SearchType.COUNTRY));
    }

    private void navigateToItemsScreen(SearchModel.SearchType itemType) {
        SearchFragmentDirections.ActionMealsListScreenFragmentToItemsScreenFragment action =
                SearchFragmentDirections.actionMealsListScreenFragmentToItemsScreenFragment(itemType);
        navController.navigate(action);
    }

    private void updateFilterTypeChipsState() {
        boolean hasCategory = false;
        boolean hasIngredient = false;
        boolean hasCountry = false;

        for (SearchModel filter : activeFilters) {
            switch (filter.getType()) {
                case CATEGORY:
                    hasCategory = true;
                    break;
                case INGREDIENT:
                    hasIngredient = true;
                    break;
                case COUNTRY:
                    hasCountry = true;
                    break;
            }
        }

        binding.chipCategory.setChecked(hasCategory);
        binding.chipIngredient.setChecked(hasIngredient);
        binding.chipCountry.setChecked(hasCountry);
    }

    @Override
    public void onLoad() {
        loading.setVisibility(VISIBLE);
        errorText.setVisibility(GONE);
    }

    @Override
    public void onSuccess(List<SearchedMealResponse> data) {
        loading.setVisibility(GONE);
        errorText.setVisibility(GONE);

        if (data.isEmpty()) {
            errorText.setVisibility(VISIBLE);
            errorText.setText("No Meals Found");
            return;
        }
        errorText.setVisibility(GONE);

        Log.i(TAG, "Loaded Meals: " + data.size());
        adapter.setMeals(data);
    }

    @Override
    public void onFailure(Failure failure) {
        loading.setVisibility(GONE);
        errorText.setVisibility(VISIBLE);
        errorText.setText(failure.getMessage());
    }

    private void restoreFiltersFromNavigation() {
        NavBackStackEntry currentEntry = navController.getCurrentBackStackEntry();

        if (currentEntry != null) {
            currentEntry.getSavedStateHandle()
                    .<SearchModel>getLiveData("selected_filter")
                    .observe(getViewLifecycleOwner(),
                            filter -> {
                                if (filter != null) {
                                    addFilter(filter);
                                    navController.getCurrentBackStackEntry()
                                            .getSavedStateHandle()
                                            .remove("selected_filter");
                                }
                            }
                    );
        }
        refreshFilterChips();
    }

    private void addFilter(SearchModel filter) {
        int existingFilterIndex = -1;

        for (int i = 0; i < activeFilters.size(); i++) {
            SearchModel existingFilter = activeFilters.get(i);
            if (existingFilter.getType() == filter.getType() &&
                    existingFilter.getName().equals(filter.getName())) {
                existingFilterIndex = i;
                break;
            }
        }

        if (existingFilterIndex == -1) {
            activeFilters.add(filter);
            addFilterChip(filter);
            updateFilterTypeChipsState();
            applyFilters();
        }
    }

    private void addFilterChip(SearchModel filter) {
        Chip chip = new Chip(requireContext());
        chip.setId(View.generateViewId());
        chip.setText(filter.getName());
        chip.setCloseIconVisible(true);
        chip.setClickable(true);
        chip.setCheckable(false);

        chip.setChipBackgroundColor(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.success_light)));
        chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary));
        chip.setCloseIconTint(ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.text_primary)));

        chip.setOnCloseIconClickListener(v -> removeFilter(filter));
        chip.setOnClickListener(v -> removeFilter(filter));

        binding.chipGroupActiveFilters.addView(chip);
    }

    private void removeFilter(SearchModel filter) {
        activeFilters.remove(filter);
        refreshFilterChips();
        updateFilterTypeChipsState();
        applyFilters();
    }

    private void refreshFilterChips() {
        binding.chipGroupActiveFilters.removeAllViews();
        for (SearchModel filter : activeFilters) {
            addFilterChip(filter);
        }
    }

    private void applyFilters() {
        String currentQuery = searchText.getEditText().getText().toString().trim();

        if (!currentQuery.isEmpty()) {
            searchPresenter.searchMeals(currentQuery, activeFilters);
        } else if (activeFilters.isEmpty()) {
            searchPresenter.searchMeals(null);
        } else {
            searchPresenter.searchMeals(new ArrayList<>(activeFilters));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchRunnable != null) {
            searchHandler.removeCallbacks(searchRunnable);
        }
        binding = null;
    }
}