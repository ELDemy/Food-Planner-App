package com.dmy.foodplannerapp.presentation.search.view.items_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmy.foodplannerapp.R;
import com.dmy.foodplannerapp.data.model.dto.FilterItem;

import java.util.ArrayList;
import java.util.List;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ViewHolder> {

    private static final String TAG = "ItemsListAdapter";
    private final List<FilterItem> items;
    Context context;
    ItemsScreenFragment fragment;

    public ItemsListAdapter(Context context, ItemsScreenFragment fragment) {
        this.context = context;
        items = new ArrayList<>();
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    public void setItems(List<? extends FilterItem> items) {
        Log.i(TAG, "setting Items: " + items.size());
        this.items.clear();
        if (!items.isEmpty()) {
            this.items.addAll(items);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView title;
        ImageView image;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.tv_name);
            image = itemView.findViewById(R.id.iv_image);
            card = itemView.findViewById(R.id.cardView_image);
        }

        private void bind(FilterItem item) {
            title.setText(item.getName());
            Glide.with(context).load(item.getThumbnail()).into(image);

            card.setOnClickListener((btnView) -> {
                fragment.onItemSelected(item);
            });
        }
    }
}
