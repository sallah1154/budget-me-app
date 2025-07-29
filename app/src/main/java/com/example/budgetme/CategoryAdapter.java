package com.example.budgetme;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

interface OnCategoryClickListener {
    void onCategoryClick(Category category);
}
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private Context context;
    private OnCategoryClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;


    public CategoryAdapter(Context context, List<Category> categories,OnCategoryClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.getName());

        String icon = category.getIconName();

        if (icon.startsWith("ic_")) {
            // Try to load drawable
            int resId = context.getResources().getIdentifier(icon, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.icon.setVisibility(View.VISIBLE);
                holder.icon.setImageResource(resId);
                holder.iconText.setVisibility(View.GONE);
            } else {
                holder.icon.setVisibility(View.GONE);
                holder.iconText.setVisibility(View.VISIBLE);
                holder.iconText.setText("❓");
            }
        } else {
            // Emoji mode
            holder.icon.setVisibility(View.GONE);
            holder.iconText.setVisibility(View.VISIBLE);
            holder.iconText.setText(icon);
        }

        GradientDrawable background = (GradientDrawable) holder.categoryFrame.getBackground();
        if (selectedPosition == position) {
            background.setColor(Color.parseColor("#f9c74f"));
        } else {
            background.setColor(Color.parseColor("#eff374")); // default color
        }

        holder.itemView.setOnClickListener(v -> {
            int oldPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(oldPosition);
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView iconText;
        TextView name;
        FrameLayout categoryFrame;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.category_icon);
            iconText = itemView.findViewById(R.id.category_icon_text);
            name = itemView.findViewById(R.id.category_name);
            categoryFrame = itemView.findViewById(R.id.category_frame); // properly scoped here
        }
    }
}