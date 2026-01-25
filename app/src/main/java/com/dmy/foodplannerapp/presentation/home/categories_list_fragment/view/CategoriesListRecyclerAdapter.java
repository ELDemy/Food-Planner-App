package com.dmy.foodplannerapp.presentation.home.categories_list_fragment.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListRecyclerAdapter extends RecyclerView.Adapter<CategoriesListRecyclerAdapter.ViewHolder> {
    List<CategoryEntity> categoriesList;
    Context context;


    public CategoriesListRecyclerAdapter(Context context) {
        this.context = context;
        categoriesList = new ArrayList<>();
    }

    public void updateList(List<CategoryEntity> categoriesList) {
        this.categoriesList = categoriesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categoriesList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private static final int[] CARD_COLORS = {
                R.color.light_yellow,
                R.color.light_orange,
                R.color.light_peach,
                R.color.light_pink,
                R.color.light_red,
                R.color.light_purple,
                R.color.light_blue,
                R.color.light_cyan,
                R.color.light_green,
                R.color.light_lime
        };
        View itemView;
        ImageView imageView;
        TextView titleText;
        ConstraintLayout cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            cardView = itemView.findViewById(R.id.view_categoryCardView);
            imageView = itemView.findViewById(R.id.iv_image);
            titleText = itemView.findViewById(R.id.tv_name);
        }

        void bind(CategoryEntity category, int position) {
            int color = ContextCompat.getColor(itemView.getContext(), CARD_COLORS[position % CARD_COLORS.length]);

            cardView.setBackgroundColor(color);

            titleText.setText(category.getName());
            Glide.with(itemView).load(category.getThumbnail()).into(imageView);
        }
    }
}
