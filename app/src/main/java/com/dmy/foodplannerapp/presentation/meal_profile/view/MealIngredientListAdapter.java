package com.dmy.foodplannerapp.presentation.meal_profile.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.Ingredient;

import java.util.List;

public class MealIngredientListAdapter extends RecyclerView.Adapter<MealIngredientListAdapter.ViewHolder> {
    List<Ingredient> ingredientList;
    Context context;

    public MealIngredientListAdapter(Context context, List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @NonNull
    @Override
    public MealIngredientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_item_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealIngredientListAdapter.ViewHolder holder, int position) {
        holder.bind(ingredientList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView ingredientTitle;
        TextView ingredientQuantity;
        ImageView ingredientImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            ingredientTitle = itemView.findViewById(R.id.tv_ingredientTitle);
            ingredientQuantity = itemView.findViewById(R.id.tv_ingredientQuantity);
            ingredientImage = itemView.findViewById(R.id.img_ingredient);
        }

        void bind(Ingredient ingredient) {
            ingredientTitle.setText(ingredient.getName());
            ingredientQuantity.setText(ingredient.getQuantity());
            Glide.with(view).load(ingredient.getThumbnail()).into(ingredientImage);
        }
    }
}
