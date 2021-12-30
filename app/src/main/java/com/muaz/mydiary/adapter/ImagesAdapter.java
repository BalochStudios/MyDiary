package com.muaz.mydiary.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.databinding.ItemImageBinding;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesHolder> {

    List<Bitmap> bitmaps;
    AdapterView.OnItemClickListener onItemClickListener;

    public ImagesAdapter(List<Bitmap> bitmaps, AdapterView.OnItemClickListener onItemClickListener) {
        this.bitmaps = bitmaps;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImagesHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    public class ImagesHolder extends RecyclerView.ViewHolder {

        private final ItemImageBinding binding;

        public ImagesHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.ivImage.setImageBitmap(bitmaps.get(position));
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(null, itemView, position, 0));
        }
    }
}
