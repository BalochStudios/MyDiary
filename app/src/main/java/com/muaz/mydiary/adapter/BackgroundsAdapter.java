package com.muaz.mydiary.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.databinding.ItemBackgroundBinding;
import com.muaz.mydiary.models.Mood;

import java.util.ArrayList;

public class BackgroundsAdapter extends RecyclerView.Adapter<BackgroundsAdapter.BackgroundHolder> {

    private final ArrayList<Mood> bgArrayList;
    private final AdapterView.OnItemClickListener onItemClickListener;

    public BackgroundsAdapter(ArrayList<Mood> bgArrayList, AdapterView.OnItemClickListener onItemClickListener) {
        this.bgArrayList = bgArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BackgroundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BackgroundHolder(ItemBackgroundBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BackgroundHolder holder, int position) {
        holder.binding.ivBackground.setImageResource(bgArrayList.get(position).getMoodImage());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(null, holder.itemView, position, 0));
    }

    @Override
    public int getItemCount() {
        return bgArrayList.size();
    }

    public static class BackgroundHolder extends RecyclerView.ViewHolder {

        private ItemBackgroundBinding binding;

        public BackgroundHolder(ItemBackgroundBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
