package com.dmy.foodplannerapp.presentation.search.view.items_list;

import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.failure.Failure;
import com.dmy.foodplannerapp.data.model.dto.FilterItem;
import com.dmy.foodplannerapp.data.model.entity.SearchModel;
import com.dmy.foodplannerapp.databinding.FragmentItemsScreenFragmentBinding;
import com.dmy.foodplannerapp.presentation.search.presenter.items_list_presenter.ItemsListPresenter;
import com.dmy.foodplannerapp.presentation.search.presenter.items_list_presenter.ItemsListPresenterImpl;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ItemsScreenFragment extends Fragment implements ItemsListView {
    private static final String TAG = "ItemsScreenFragment";
    TextView title;
    CardView backBtn;
    RecyclerView recyclerView;
    FragmentItemsScreenFragmentBinding binding;
    ItemsListAdapter adapter;
    ItemsListPresenter presenter;
    SearchModel.SearchType itemType;
    NavController navController;
    TextInputLayout searchTxt;
    TextView errorTxt;

    LottieAnimationView loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemsScreenFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        if (getArguments() != null) {
            ItemsScreenFragmentArgs args = ItemsScreenFragmentArgs.fromBundle(getArguments());
            Log.i(TAG, "onViewCreated: " + args);
            itemType = args.getItemType();
        }
        initViews();
        setUpSearchText();
        setScreenTitle();
        backBtn.setOnClickListener((btnView) -> navController.navigateUp());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2); // 2 columns
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ItemsListAdapter(requireContext(), this);
        recyclerView.setAdapter(adapter);

        presenter = new ItemsListPresenterImpl(this, requireContext());
        presenter.loadItems(itemType);
    }

    void initViews() {
        title = binding.tvTitle;
        backBtn = binding.btnBackContainer;
        recyclerView = binding.recyclerItems;
        searchTxt = binding.txtFieldSearchLayout;
        loading = binding.loading;
        errorTxt = binding.tvListError;
    }

    void setUpSearchText() {
        searchTxt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.filter(charSequence.toString());
            }
        });
    }

    private void setScreenTitle() {
        String titleText;
        switch (itemType) {
            case INGREDIENT:
                titleText = "Select Ingredient";
                break;
            case COUNTRY:
                titleText = "Select Country";
                break;
            case CATEGORY:
            default:
                titleText = "Select Category";
                break;
        }
        title.setText(titleText);
    }

    @Override
    public void onSuccess(List<? extends FilterItem> data) {
        if (data.isEmpty()) {
            errorTxt.setVisibility(View.VISIBLE);
            errorTxt.setText("No items found");
            return;
        }
        adapter.setItems(data);
    }

    @Override
    public void onFailure(Failure failure) {
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(failure.getMessage());
        Log.e(TAG, "Failed to load items: " + failure);
    }

    @Override
    public void onLoad(boolean isLoading) {
        errorTxt.setVisibility(View.GONE);
        loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    public void onItemSelected(FilterItem selectedItem) {
        SearchModel filter = new SearchModel(itemType, selectedItem.getName());

        if (navController.getPreviousBackStackEntry() != null &&
                navController.getPreviousBackStackEntry().getDestination().getId()
                        == R.id.mealsListScreenFragment
        ) {
            navController.getPreviousBackStackEntry()
                    .getSavedStateHandle()
                    .set("selected_filter", filter);
            navController.navigateUp();

        } else {
            ItemsScreenFragmentDirections.ActionItemsScreenFragmentToMealsListScreenFragment action
                    = ItemsScreenFragmentDirections.actionItemsScreenFragmentToMealsListScreenFragment(filter);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.itemsScreenFragment, true)
                    .build();

            navController.navigate(action, navOptions);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}