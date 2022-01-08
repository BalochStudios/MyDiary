package com.muaz.mydiary.ui.others;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.databinding.ItemLayoutTagBinding;
import com.muaz.mydiary.databinding.ItemTagBinding;
import com.muaz.mydiary.models.Tag;
import com.muaz.mydiary.utils.Constants;

import java.util.List;

public class TagsAdapterClass extends RecyclerView.Adapter<TagsAdapterClass.TagsHolder> {

    private final List<Tag> tagList;

    private final AdapterView.OnItemClickListener tagClickListener;


    public TagsAdapterClass(List<Tag> tagList, AdapterView.OnItemClickListener tagClickListener) {
        this.tagList = tagList;
        this.tagClickListener = tagClickListener;
    }

    @NonNull
    @Override
    public TagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagsHolder(ItemLayoutTagBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagsHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class TagsHolder extends RecyclerView.ViewHolder {

        private final ItemLayoutTagBinding binding;

        public TagsHolder(ItemLayoutTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
           // binding.tvTag.setOnClickListener(view -> tagClickListener.onItemClick(null, binding.tvTag, position, 0));

            Tag currentTag = tagList.get(position);
            binding.tvTag.setText("#"+currentTag.getTag());
            itemView.setOnClickListener(view -> tagClickListener.onItemClick(null, itemView, position, 0));
        }
    }
}
