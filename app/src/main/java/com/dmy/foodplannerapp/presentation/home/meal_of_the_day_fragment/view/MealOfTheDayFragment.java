package com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.view;

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
import com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.presenter.MealOfTheDayPresenter;
import com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.presenter.MealOfTheDayPresenterImpl;
import com.dmy.foodplannerapp.presentation.reusable_components.CustomSnackBar;

public class MealOfTheDayFragment extends Fragment implements MealOfTheDayView {
    CardView featuredMealCard;
    MealOfTheDayPresenter mealOfTheDayPresenter;
    ImageView featuredImage;
    LottieAnimationView loadingAnimation;
    TextView title;
    TextView category;
    TextView country;

    CardView favBtn;

    MealEntity meal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealOfTheDayPresenter = new MealOfTheDayPresenterImpl(requireContext(), this);
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
        favBtn = featuredMealCard.findViewById(R.id.btn_favorite);

        mealOfTheDayPresenter.getMealOfTheDay();

        featuredMealCard.setOnClickListener(btnView -> {
            CustomSnackBar.showInfo(getView(), "Meal clicked");
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mealProfileFragment);
        });

        favBtn.setOnClickListener(btnView -> {
            changeFavouriteIcon(!meal.isFavourite());
            mealOfTheDayPresenter.addToFavourite(meal);
        });
    }

    @Override
    public void loadMealOfTheDay(boolean isLoading) {
        loadingAnimation.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        featuredMealCard.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showMealOfTheDay(MealEntity meal) {
        this.meal = meal;
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

    private void changeFavouriteIcon(boolean isFavourite) {
        ImageView heartImage = favBtn.findViewById(R.id.iv_heart);
        heartImage.setImageResource(isFavourite ? R.drawable.heart_filled : R.drawable.heart);
    }

    @Override
    public void changeFavouriteState(boolean isFavourite) {
        changeFavouriteIcon(isFavourite);
        if (isFavourite) {
            CustomSnackBar.showInfo(getView(), meal.getName() + " added to favorites");
        } else {
            CustomSnackBar.showInfo(getView(), meal.getName() + " removed from favorites");
        }
    }
}