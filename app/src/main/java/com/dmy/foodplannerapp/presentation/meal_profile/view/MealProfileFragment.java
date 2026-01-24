package com.dmy.foodplannerapp.presentation.meal_profile.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.meal_profile.presenter.MealProfilePresenter;
import com.dmy.foodplannerapp.presentation.meal_profile.presenter.MealProfilePresenterImpl;

public class MealProfileFragment extends Fragment implements MealProfileView {
    MealEntity meal;

    CardView backButton;
    CardView favoriteButton;
    ImageView heartImage;
    Button addToWeeklyBtn;
    TextView titleText;
    TextView categoryText;
    TextView countryText;
    RecyclerView ingredientsRecyclerView;
    TextView descriptionText;
    ImageView mealImage;


    MealProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MealProfilePresenterImpl(requireContext(), this);
        meal = MealProfileFragmentArgs.fromBundle(getArguments()).getMeal();

        titleText = view.findViewById(R.id.tv_name);
        descriptionText = view.findViewById(R.id.tv_description);
        categoryText = view.findViewById(R.id.tv_category);
        countryText = view.findViewById(R.id.tv_country);
        mealImage = view.findViewById(R.id.img_meal);
        backButton = view.findViewById(R.id.btn_back_container);
        favoriteButton = view.findViewById(R.id.btn_favorite);
        heartImage = favoriteButton.findViewById(R.id.iv_heart);
        addToWeeklyBtn = view.findViewById(R.id.btn_addToWeekly);

        updateData(view);

        ingredientsRecyclerView = view.findViewById(R.id.recycler_ingredients);
        ingredientsRecyclerView.setAdapter(new MealIngredientListAdapter(getActivity(), meal.getIngredients()));
    }

    void updateData(View view) {
        titleText.setText(meal.getName());
        descriptionText.setText(meal.getInstructions());
        categoryText.setText(meal.getCategory());
        countryText.setText(meal.getArea());
        Glide.with(view).load(meal.getThumbnail()).into(mealImage);
        changeFavoriteState(meal.isFavourite());

        favoriteButton.setOnClickListener((cardView) -> {
            changeFavoriteState(!meal.isFavourite());
            presenter.changeFavourite(meal);
        });

        backButton.setOnClickListener(view1 -> getParentFragmentManager().popBackStack());

        addToWeeklyBtn.setOnClickListener(view2 -> getParentFragmentManager().popBackStack());
    }

    @Override
    public void changeFavoriteState(boolean isFavorite) {
        heartImage.setImageResource(isFavorite ? R.drawable.heart_filled : R.drawable.heart);
    }
}