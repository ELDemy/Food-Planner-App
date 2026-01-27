package com.dmy.foodplannerapp.presentation.favourite.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;
import com.dmy.foodplannerapp.presentation.favourite.presenter.FavoritePresenter;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.FavouriteMealViewHolder> {
    Context context;

    List<MealEntity> mealsList;
    FavoritePresenter favoritePresenter;

    FavouriteMealsAdapter(Context context, FavoritePresenter favoritePresenter) {
        this.context = context;
        mealsList = new ArrayList<>();
        this.favoritePresenter = favoritePresenter;
    }

    public void UpdateMealsList(List<MealEntity> mealsList) {
        this.mealsList = mealsList;
        Log.i("onLoadSuccess", "getFavouriteMeals in Fragment: " + mealsList);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_card, parent, false);
        return new FavouriteMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMealViewHolder holder, int position) {
        holder.bind(mealsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    class FavouriteMealViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView mealImage;
        CardView heartBtn;
        ImageView heartImage;
        TextView mealTitle;
        TextView category;

        public FavouriteMealViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mealImage = itemView.findViewById(R.id.img_meal);
            heartImage = itemView.findViewById(R.id.iv_heart);
            mealTitle = itemView.findViewById(R.id.tv_mealTitle);
            category = itemView.findViewById(R.id.tv_category);
            heartBtn = itemView.findViewById(R.id.btn_favorite);
        }

        void bind(MealEntity meal) {
            mealTitle.setText(meal.getName());
            category.setText(meal.getCategory());
            heartImage.setImageResource(R.drawable.heart_filled);

            Glide.with(itemView)
                    .load(meal.getThumbnail())
                    .into(mealImage);

            heartBtn.setOnClickListener(v -> {
                favoritePresenter.changeFavourite(meal);
            });

            itemView.setOnClickListener((cardView) -> {
                FavouriteFragmentDirections.ActionFavouriteFragmentToMealProfileFragment action = FavouriteFragmentDirections
                        .actionFavouriteFragmentToMealProfileFragment(meal);

                Navigation.findNavController(itemView).navigate(action);
            });
        }
    }

}
