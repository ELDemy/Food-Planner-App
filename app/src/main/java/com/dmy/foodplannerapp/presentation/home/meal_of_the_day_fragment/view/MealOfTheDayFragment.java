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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.presenter.MealOfTheDayPresenter;
import com.dmy.foodplannerapp.presentation.home.meal_of_the_day_fragment.presenter.MealOfTheDayPresenterImpl;
import com.dmy.foodplannerapp.presentation.home.view.HomeFragmentDirections;
import com.dmy.foodplannerapp.presentation.home.view.HomeRefreshViewModel;
import com.dmy.foodplannerapp.utils.CustomSnackBar;

public class MealOfTheDayFragment extends Fragment implements MealOfTheDayView {
    private static final String TAG = "MealOfTheDayFragment";
    CardView featuredMealCard;
    MealOfTheDayPresenter mealOfTheDayPresenter;
    ImageView featuredImage;
    LottieAnimationView loadingAnimation;
    TextView title;
    TextView category;
    TextView country;
    CardView favBtn;
    MealEntity meal;
    TextView errorTxt;
    private HomeRefreshViewModel sharedViewModel;

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
        errorTxt = view.findViewById(R.id.tv_Error);

        mealOfTheDayPresenter.getMealOfTheDay();

        featuredMealCard.setOnClickListener(btnView -> {
            HomeFragmentDirections.ActionHomeFragmentToMealProfileFragment action = HomeFragmentDirections
                    .actionHomeFragmentToMealProfileFragment(null, meal);

            Navigation.findNavController(view).navigate(action);
        });

        favBtn.setOnClickListener(btnView -> {
            changeFavouriteIcon(!meal.isFavourite());
            mealOfTheDayPresenter.changeFavourite(meal);
        });

        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomeRefreshViewModel.class);

        sharedViewModel.getRefreshTrigger().observe(getViewLifecycleOwner(), shouldRefresh -> {
            if (shouldRefresh != null && shouldRefresh) {
                mealOfTheDayPresenter.getMealOfTheDay();
            }
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
        changeFavouriteIcon(meal.isFavourite());

        Glide.with(requireContext())
                .load(meal.getThumbnail())
                .into(featuredImage);
    }

    @Override
    public void errorMealOfTheDay(String message) {
        featuredMealCard.setVisibility(View.GONE);
        errorTxt.setVisibility(View.VISIBLE);
        errorTxt.setText(message);
    }

    private void changeFavouriteIcon(boolean isFavourite) {
        ImageView heartImage = favBtn.findViewById(R.id.iv_heart);
        heartImage.setImageResource(isFavourite ? R.drawable.heart_filled : R.drawable.heart);
    }

    @Override
    public void changeFavoriteState(boolean isFavourite) {
        changeFavouriteIcon(isFavourite);
        if (isFavourite) {
            CustomSnackBar.showInfo(getView(), meal.getName() + " added to favorites");
        } else {
            CustomSnackBar.showInfo(getView(), meal.getName() + " removed from favorites");
        }
    }
}