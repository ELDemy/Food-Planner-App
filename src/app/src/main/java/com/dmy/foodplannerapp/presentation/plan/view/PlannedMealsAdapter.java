package com.dmy.foodplannerapp.presentation.plan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.entity.MealPlanWithDetails;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.ViewHolder> {

    private final Context context;
    private final List<MealPlanWithDetails> meals;
    private final OnMealActionListener onMealClick;

    public PlannedMealsAdapter(Context context, OnMealActionListener listener) {
        this.context = context;
        this.meals = new ArrayList<>();
        this.onMealClick = listener;
    }

    public void setMeals(List<MealPlanWithDetails> newMeals) {
        this.meals.clear();
        if (newMeals != null) {
            this.meals.addAll(newMeals);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.planned_meal_item, parent, false);
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

    public interface OnMealActionListener {
        void onMealRemove(MealPlanWithDetails mealPlan);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final ImageView imgMeal;
        private final TextView tvMealName;
        private final TextView tvMealCategory;
        private final MaterialCardView btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_planned_meal);
            imgMeal = itemView.findViewById(R.id.img_meal);
            tvMealName = itemView.findViewById(R.id.tv_meal_name);
            tvMealCategory = itemView.findViewById(R.id.tv_meal_category);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }

        public void bind(MealPlanWithDetails mealPlan) {
            if (mealPlan.meal != null) {
                tvMealName.setText(mealPlan.meal.getName());
                tvMealCategory.setText(mealPlan.meal.getCategory());

                Glide.with(context)
                        .load(mealPlan.meal.getThumbnail())
                        .placeholder(R.drawable.feature_image_placeholder)
                        .into(imgMeal);

                cardView.setOnClickListener(v -> {
                    PlanFragmentDirections.ActionPlanFragmentToMealProfileFragment action = PlanFragmentDirections
                            .actionPlanFragmentToMealProfileFragment(null, mealPlan.meal);

                    Navigation.findNavController(itemView).navigate(action);
                });

                btnRemove.setOnClickListener(v -> onMealClick.onMealRemove(mealPlan));
            }
        }
    }
}
