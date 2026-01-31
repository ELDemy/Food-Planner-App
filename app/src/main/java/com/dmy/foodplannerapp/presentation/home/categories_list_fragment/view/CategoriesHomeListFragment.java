package com.dmy.foodplannerapp.presentation.home.categories_list_fragment.view;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.dto.CategoryDTO;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.presentation.home.categories_list_fragment.presenter.CategoriesListPresenter;
import com.dmy.foodplannerapp.presentation.home.categories_list_fragment.presenter.CategoriesListPresenterImpl;
import com.dmy.foodplannerapp.presentation.home.view.HomeFragmentDirections;
import com.dmy.foodplannerapp.presentation.home.view.HomeRefreshViewModel;

import java.util.List;

public class CategoriesHomeListFragment extends Fragment implements CategoriesListView {
    RecyclerView rvCategories;
    CategoriesHomeListRecyclerAdapter adapter;
    TextView errTxt;
    LottieAnimationView loading;
    TextView seeAllTxt;

    CategoriesListPresenter presenter;
    HomeRefreshViewModel sharedViewModel;
    private List<CategoryDTO> activeCategories = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_categories_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCategories = view.findViewById(R.id.rv_categories);
        errTxt = view.findViewById(R.id.tv_error);
        loading = view.findViewById(R.id.loading);
        seeAllTxt = view.findViewById(R.id.tv_seeAll);
        presenter = new CategoriesListPresenterImpl(this, requireContext());

        adapter = new CategoriesHomeListRecyclerAdapter(requireContext());
        rvCategories.setAdapter(adapter);

        presenter.getCategories();

        seeAllTxt.setOnClickListener(v -> {
            HomeFragmentDirections.ActionHomeFragmentToItemsScreenFragment2 action = HomeFragmentDirections
                    .actionHomeFragmentToItemsScreenFragment2(SearchModel.SearchType.CATEGORY);

            NavHostFragment.findNavController(CategoriesHomeListFragment.this).navigate(action);
        });
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomeRefreshViewModel.class);

        sharedViewModel.getRefreshTrigger().observe(getViewLifecycleOwner(), shouldRefresh -> {
            if (shouldRefresh != null && shouldRefresh) {
                if (activeCategories == null || activeCategories.isEmpty()) {
                    presenter.getCategories();
                } else {
                    updateCategories(activeCategories);
                }
            }
        });
    }

    @Override
    public void onLoading(boolean isLoading) {
        errTxt.setVisibility(View.GONE);
        loading.setVisibility(isLoading ? VISIBLE : View.GONE);
        rvCategories.setVisibility(isLoading ? View.GONE : VISIBLE);
    }

    @Override
    public void onFailure(String message) {
        errTxt.setVisibility(VISIBLE);
        errTxt.setText(message);
        rvCategories.setVisibility(View.GONE);
    }

    @Override
    public void updateCategories(List<CategoryDTO> categories) {
        activeCategories = categories;
        if (categories.isEmpty()) {
            errTxt.setVisibility(VISIBLE);
            errTxt.setText("No Categories Found");
        }
        adapter.updateList(categories);
    }
}