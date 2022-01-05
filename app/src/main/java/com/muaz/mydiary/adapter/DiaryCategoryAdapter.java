package com.muaz.mydiary.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ItemCategoryDiaryBinding;
import com.muaz.mydiary.models.DiaryCategory;

import java.util.List;

public class DiaryCategoryAdapter extends RecyclerView.Adapter<DiaryCategoryAdapter.CategoryHolder> {

    private final List<DiaryCategory> diaryCategories;
    private int selectedPosition;

    public DiaryCategoryAdapter(List<DiaryCategory> diaryCategories) {
        this.diaryCategories = diaryCategories;
        this.selectedPosition = 0;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(ItemCategoryDiaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.ivCategory.setImageResource(diaryCategories.get(position).getCategoryImage());
        holder.binding.tvCategory.setText(diaryCategories.get(position).getCategoryName());
        Resources resources = holder.itemView.getContext().getResources();

        if (diaryCategories.get(position).isSelectedCategory()) {
            holder.itemView.setBackgroundColor(resources.getColor(R.color.black_dim));
            holder.binding.tvCategory.setTextColor(resources.getColor(R.color.white));
        } else {
            holder.itemView.setBackgroundColor(resources.getColor(R.color.null_color));
            holder.binding.tvCategory.setTextColor(resources.getColor(R.color.black));
        }

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = position;
            for (int i = 0; i < diaryCategories.size(); i++) {
                diaryCategories.get(i).setSelectedCategory(i == position);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return diaryCategories.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {

        private final ItemCategoryDiaryBinding binding;

        public CategoryHolder(ItemCategoryDiaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
