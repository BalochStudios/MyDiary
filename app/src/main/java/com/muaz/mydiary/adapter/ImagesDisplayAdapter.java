package com.muaz.mydiary.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.databinding.ItemImageDetailBinding;

import java.util.List;

public class ImagesDisplayAdapter extends RecyclerView.Adapter<ImagesDisplayAdapter.ImageDisplayHolder> {

    private final List<Bitmap> bitmapList;

    public ImagesDisplayAdapter(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    @NonNull
    @Override
    public ImageDisplayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageDisplayHolder(ItemImageDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDisplayHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    class ImageDisplayHolder extends RecyclerView.ViewHolder {

        private final ItemImageDetailBinding binding;

        public ImageDisplayHolder(ItemImageDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.ivDetail.setImageBitmap(bitmapList.get(position));
        }
    }

}
