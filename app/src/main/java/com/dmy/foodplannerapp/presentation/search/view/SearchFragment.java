package com.dmy.foodplannerapp.presentation.search.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.dto.SearchedMealResponse;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.databinding.FragmentSearchMealsScreenBinding;
import com.dmy.foodplannerapp.presentation.search.presenter.SearchPresenter;
import com.dmy.foodplannerapp.presentation.search.presenter.SearchPresenterImpl;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView {

    private static final String TAG = "SearchFragment";
    SearchModel arguments;
    SearchPresenter searchPresenter;
    FragmentSearchMealsScreenBinding binding;
    RecyclerView recyclerView;
    SearchListAdapter adapter;
    CardView backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchMealsScreenBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            SearchFragmentArgs args = SearchFragmentArgs.fromBundle(getArguments());
            arguments = args.getData();
        }

        backBtn = binding.btnBackContainer;
        backBtn.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(SearchFragment.this)
                    .popBackStack(R.id.homeFragment, false);
        });
        recyclerView = view.findViewById(R.id.recycler_meals);
        adapter = new SearchListAdapter(requireContext());
        recyclerView.setAdapter(adapter);

        searchPresenter = new SearchPresenterImpl(this, requireContext());
        searchPresenter.searchMeals(arguments);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onSuccess(List<SearchedMealResponse> data) {
        Log.i(TAG, "Loaded Meals: " + data.size());
        adapter.setMeals(data);
    }

    @Override
    public void onFailure(Failure failure) {

    }
}