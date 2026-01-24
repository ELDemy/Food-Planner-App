package com.dmy.foodplannerapp.presentation.favourite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.presenter.FavoritePresenter;
import com.dmy.foodplannerapp.presentation.favourite.presenter.FavoritePresenterImpl;

import java.util.List;

public class FavouriteFragment extends Fragment implements FavoritesView {
    FavoritePresenter presenter;

    RecyclerView recyclerView;
    FavouriteMealsAdapter adapter;
    TextView emptyText;
    TextView countText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FavoritePresenterImpl(getActivity(), this);
        emptyText = view.findViewById(R.id.tv_emptyList);
        countText = view.findViewById(R.id.tv_count);

        recyclerView = view.findViewById(R.id.rv_favMeals);
        adapter = new FavouriteMealsAdapter(getActivity(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter.loadFavourites();
    }


    @Override
    public void onLoadSuccess(LiveData<List<MealEntity>> meals) {
        meals.observe(
                this,
                mealsList -> {
                    if (mealsList != null) {
                        countText.setText(mealsList.size() + "");
                        emptyText.setVisibility(View.GONE);
                        adapter.UpdateMealsList(mealsList);

                        if (mealsList.isEmpty()) {
                            countText.setText(0 + "");
                            emptyText.setVisibility(View.VISIBLE);
                        }
                    } else {
                        countText.setText(0 + "");
                        emptyText.setVisibility(View.VISIBLE);
                    }

                }
        );

    }

}