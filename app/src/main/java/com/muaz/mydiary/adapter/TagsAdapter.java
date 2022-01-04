package com.muaz.mydiary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.databinding.ItemTagBinding;
import com.muaz.mydiary.models.Tag;
import com.muaz.mydiary.utils.Constants;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsHolder> {

    private final List<Tag> tagList;
    private final int type;
    private final AdapterView.OnItemClickListener tagClickListener;
    private final AdapterView.OnItemClickListener removeTagClickListener;

    public TagsAdapter(List<Tag> tagList, int type, AdapterView.OnItemClickListener tagClickListener, AdapterView.OnItemClickListener removeTagClickListener) {
        this.tagList = tagList;
        this.type = type;
        this.tagClickListener = tagClickListener;
        this.removeTagClickListener = removeTagClickListener;
    }

    @NonNull
    @Override
    public TagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagsHolder(ItemTagBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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

        private final ItemTagBinding binding;

        public TagsHolder(ItemTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            if (type == Constants.TAG_ADAPTER_TYPE) {
                binding.ivRemoveTag.setVisibility(View.GONE);
                itemView.setOnClickListener(view -> tagClickListener.onItemClick(null, binding.tvTag, position, 0));
            } else {
                binding.ivRemoveTag.setVisibility(View.VISIBLE);
                binding.tvTag.setOnClickListener(view -> tagClickListener.onItemClick(null, binding.tvTag, position, 0));
            }
            binding.ivRemoveTag.setOnClickListener(view -> removeTagClickListener.onItemClick(null, binding.ivRemoveTag, position, 0));
            Tag currentTag = tagList.get(position);
            binding.tvTag.setText(currentTag.getTag());
        }
    }
}
