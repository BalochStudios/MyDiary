package com.muaz.mydiary.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.databinding.ItemBackgroundBinding;
import com.muaz.mydiary.databinding.ItemColorBinding;
import com.muaz.mydiary.models.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorsHolder> {

    private final List<Color> colors;
    private final AdapterView.OnItemClickListener onItemClickListener;

    public ColorsAdapter(List<Color> colors, AdapterView.OnItemClickListener onItemClickListener) {
        this.colors = colors;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ColorsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColorsHolder(ItemColorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorsHolder holder, int position) {
        holder.binding.viewColor.setBackgroundResource(colors.get(position).getColorResId());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(null, holder.itemView, position, 0));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ColorsHolder extends RecyclerView.ViewHolder {

        private final ItemColorBinding binding;

        public ColorsHolder(ItemColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
