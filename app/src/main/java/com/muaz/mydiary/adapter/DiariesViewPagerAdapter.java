package com.muaz.mydiary.adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ItemDiaryDisplayBinding;
import com.muaz.mydiary.models.Color;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.models.DiaryCategory;
import com.muaz.mydiary.models.Mood;
import com.muaz.mydiary.ui.diary.ImagesDisplayActivity;
import com.muaz.mydiary.utils.Constants;
import com.muaz.mydiary.utils.DataSource;

import java.util.List;

public class DiariesViewPagerAdapter extends RecyclerView.Adapter<DiariesViewPagerAdapter.DiariesVPHolder> {

    private final List<Diary> diaryList;

    public DiariesViewPagerAdapter(List<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    @NonNull
    @Override
    public DiariesVPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiariesVPHolder(ItemDiaryDisplayBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiariesVPHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public class DiariesVPHolder extends RecyclerView.ViewHolder {

        ItemDiaryDisplayBinding binding;

        public DiariesVPHolder(ItemDiaryDisplayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            Diary diary = diaryList.get(position);
            List<DiaryCategory> diaryCategories = DataSource.getAllCategories();
            List<Mood> moodList = DataSource.getAllMoods();
            List<Mood> backgrounds = DataSource.getAllBitmaps();
            List<String> fonts = DataSource.getAllFonts(itemView.getContext());
            List<Color> colors = DataSource.getAllColors();

            binding.tvDiaryDisplayDate.setText(diary.getDate());
            binding.tvDiaryDisplayTitle.setText(diary.getTitle());
            binding.tvDiaryDisplayStory.setText(diary.getDescription());
            binding.tvDiaryCategory.setText(diaryCategories.get(diary.getCategoryId()).getCategoryName());
            binding.ivMood.setImageResource(moodList.get(diary.getMoodId()).getMoodImage());

            Resources resources = itemView.getContext().getResources();

            if (diary.getBackgroundId() != Constants.DEFAULT) {
                binding.getRoot().setBackgroundResource(backgrounds.get(diary.getBackgroundId()).getMoodImage());
            } else {
                binding.getRoot().setBackgroundResource(R.color.screen_bg);
            }
            Typeface typeface;
            if (diary.getFontId() != Constants.DEFAULT) {
                typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/" + fonts.get(diary.getFontId()));
            } else {
                typeface = Typeface.DEFAULT;
            }
            binding.tvDiaryCategory.setTypeface(typeface);
            binding.tvDiaryDisplayTitle.setTypeface(typeface);
            binding.tvDiaryDisplayDate.setTypeface(typeface);
            binding.tvDiaryDisplayStory.setTypeface(typeface);

            int colorId;
            if (diary.getTextColorId() != Constants.DEFAULT) {
                colorId = resources.getColor(colors.get(diary.getTextColorId()).getColorResId());
            } else {
                colorId = resources.getColor(R.color.black);
            }
            binding.tvDiaryDisplayDate.setTextColor(colorId);
            binding.tvDiaryDisplayTitle.setTextColor(colorId);
            binding.tvDiaryDisplayStory.setTextColor(colorId);
            binding.tvDiaryCategory.setTextColor(colorId);

            if (diary.getTextDirection() == Constants.TEXT_DIRECTION_CENTER) {
                binding.tvDiaryDisplayTitle.setGravity(Gravity.CENTER);
                binding.tvDiaryDisplayStory.setGravity(Gravity.CENTER);
                binding.tvDiaryCategory.setGravity(Gravity.CENTER);
            } else {
                if (diary.getTextDirection() == Constants.TEXT_DIRECTION_RIGHT) {
                    binding.tvDiaryDisplayTitle.setGravity(Gravity.END);
                    binding.tvDiaryDisplayStory.setGravity(Gravity.END);
                    binding.tvDiaryCategory.setGravity(Gravity.END);
                } else {
                    binding.tvDiaryDisplayTitle.setGravity(Gravity.START);
                    binding.tvDiaryDisplayStory.setGravity(Gravity.START);
                    binding.tvDiaryCategory.setGravity(Gravity.START);
                }
            }

            TagsAdapter tagsAdapter = new TagsAdapter(diary.getTagsList(),
                    Constants.TAG_ADAPTER_TYPE,
                    (adapterView, view, i, l) -> {
                    },
                    (adapterView, view, i, l) -> {
                    });
            binding.rvTags.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    RecyclerView.HORIZONTAL,
                    false));
            binding.rvTags.setAdapter(tagsAdapter);
            ImagesAdapter imagesAdapter = new ImagesAdapter(diary.getImageList(),
                    (adapterView, view, i, l) -> {
                        Intent intent = new Intent(view.getContext(), ImagesDisplayActivity.class);
                        intent.putExtra(Constants.INTENT_BITMAP_KEY, new Gson().toJson(diary.getImageList()));
                        intent.putExtra(Constants.INTENT_BITMAP_POSITION_KEY, i);
                        itemView.getContext().startActivity(intent);
                    }, Constants.IMAGES_ADAPTER_BIG_TYPE);
            binding.rvImages.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    RecyclerView.HORIZONTAL,
                    false));
            binding.rvImages.setAdapter(imagesAdapter);
        }
    }
}
