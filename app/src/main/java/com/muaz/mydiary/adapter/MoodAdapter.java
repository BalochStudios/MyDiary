package com.muaz.mydiary.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ItemMoodBinding;
import com.muaz.mydiary.models.Mood;
import com.muaz.mydiary.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.List;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodHolder> {

    private final List<Mood> moodArrayList;
    private int selectedPosition;

    public MoodAdapter(List<Mood> moodArrayList) {
        this.moodArrayList = moodArrayList;
        this.selectedPosition = 0;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @NonNull
    @Override
    public MoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoodHolder(ItemMoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MoodHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.ivEmoji.setImageResource(moodArrayList.get(position).getMoodImage());

        if (moodArrayList.get(position).isSelectedEmoji()) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.black_dim));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.null_color));
        }

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.black_dim));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.null_color));
        }

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = position;
            for (int i = 0; i < moodArrayList.size(); i++) {
                moodArrayList.get(i).setSelectedEmoji(i == position);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return moodArrayList.size();
    }

    public static class MoodHolder extends RecyclerView.ViewHolder {

        private final ItemMoodBinding binding;

        public MoodHolder(ItemMoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
