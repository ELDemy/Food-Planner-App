package com.dmy.foodplannerapp.presentation.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.home.presenter.HomePresenter;
import com.dmy.foodplannerapp.presentation.home.presenter.HomePresenterImpl;
import com.dmy.foodplannerapp.presentation.home.presenter.HomeView;
import com.dmy.foodplannerapp.presentation.reusable_components.CustomSnackBar;

public class MealOfTheDayFragment extends Fragment implements HomeView {
    CardView featuredMealCard;
    HomePresenter homePresenter;
    ImageView featuredImage;
    LottieAnimationView loadingAnimation;
    TextView title;
    TextView category;
    TextView country;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_of_the_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingAnimation = view.findViewById(R.id.load_mealOfTheDay);
        featuredMealCard = view.findViewById(R.id.card_featuredContainer);
        featuredImage = featuredMealCard.findViewById(R.id.img_mealOfTheDay);
        title = featuredMealCard.findViewById(R.id.tv_dayMealTitle);
        country = featuredMealCard.findViewById(R.id.tv_dayMealCountry);
        category = featuredMealCard.findViewById(R.id.tv_dayMealCategory);

        homePresenter.getMealOfTheDay();

        featuredMealCard.setOnClickListener(btnView -> {
            CustomSnackBar.showInfo(getView(), "Meal clicked");
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mealProfileFragment);
        });
    }

    @Override
    public void loadMealOfTheDay(boolean isLoading) {
        loadingAnimation.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        featuredMealCard.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showMealOfTheDay(MealEntity meal) {
        title.setText(meal.getName());
        category.setText(meal.getCategory());
        country.setText(meal.getArea());

        Glide.with(requireContext())
                .load(meal.getThumbnail())
                .into(featuredImage);
    }

    @Override
    public void errorMealOfTheDay(String message) {
        featuredImage.setVisibility(View.GONE);
    }
}