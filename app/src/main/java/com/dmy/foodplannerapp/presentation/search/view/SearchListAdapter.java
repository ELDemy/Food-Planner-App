package com.dmy.foodplannerapp.presentation.search.view;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    List<MealEntity> meals;
    Context context;

    public SearchListAdapter(Context context) {
        this.context = context;
        meals = new ArrayList<>();
    }

    public void setMeals(List<MealEntity> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView title;
        TextView category;
        CardView favBtn;

        ImageView heartImage;
        ImageView mealImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            title = itemView.findViewById(R.id.tv_mealTitle);
            category = itemView.findViewById(R.id.tv_category);
            favBtn = itemView.findViewById(R.id.btn_favorite);
            heartImage = itemView.findViewById(R.id.iv_heart);
            mealImage = itemView.findViewById(R.id.img_meal);
        }

        void bind(MealEntity meal) {
            title.setText(meal.getName());
            category.setText(meal.getCategory());
            Glide.with(context).load(meal.getThumbnail()).into(mealImage);
            favBtn.setVisibility(View.GONE);
            heartImage.setImageResource(meal.isFavourite() ? R.drawable.heart_filled : R.drawable.heart);

            view.setOnClickListener((btnView) -> {
                SearchFragmentDirections.ActionMealsListScreenFragmentToMealProfileFragment action = SearchFragmentDirections
                        .actionMealsListScreenFragmentToMealProfileFragment(meal);

                Navigation.findNavController(itemView).navigate(action);
            });
        }
    }
}
