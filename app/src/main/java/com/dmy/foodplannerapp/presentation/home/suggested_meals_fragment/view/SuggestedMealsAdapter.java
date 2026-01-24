package com.dmy.foodplannerapp.presentation.home.suggested_meals_fragment.view;

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
import com.dmy.foodplannerapp.data.model.MealEntity;
import com.dmy.foodplannerapp.presentation.home.view.HomeFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class SuggestedMealsAdapter extends RecyclerView.Adapter<SuggestedMealsAdapter.ViewHolder> {

    Context context;
    List<MealEntity> mealsList;

    public SuggestedMealsAdapter(Context context) {
        this.context = context;
        mealsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.small_meal_card, parent, false);
        return new ViewHolder(view);
    }

    public void updateList(List<MealEntity> mealsList) {
        this.mealsList = mealsList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mealsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tvMealName;
        ImageView ivMealImage;

        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cardView = itemView.findViewById(R.id.cardView_meal);
            tvMealName = itemView.findViewById(R.id.tv_meal_name);
            ivMealImage = itemView.findViewById(R.id.iv_meal_image);

        }

        void bind(MealEntity meal) {
            tvMealName.setText(meal.getName());
            Glide.with(context).load(meal.getThumbnail()).into(ivMealImage);

            itemView.setOnClickListener((btnView) -> {
                navigate(meal);
            });
            cardView.setOnClickListener((cardView) -> {
                navigate(meal);
            });
        }

        private void navigate(MealEntity meal) {
            HomeFragmentDirections.ActionHomeFragmentToMealProfileFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealProfileFragment(meal);

            Navigation.findNavController(itemView).navigate(action);
        }
    }


}
