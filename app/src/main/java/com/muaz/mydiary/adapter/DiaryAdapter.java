package com.muaz.mydiary.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.databinding.ItemDiaryBinding;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.models.Mood;
import com.muaz.mydiary.utils.Constants;
import com.muaz.mydiary.utils.DataSource;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryHolder> {

    private final List<Diary> diaryList;
    private final List<Diary> backUpDiaryList;
    private AdapterView.OnItemClickListener onItemClickListener;
    private String query;

    public DiaryAdapter(List<Diary> diaryList, AdapterView.OnItemClickListener onItemClickListener) {
        this.diaryList = diaryList;
        this.onItemClickListener = onItemClickListener;
        this.backUpDiaryList = new ArrayList<>(diaryList);
    }
    public void filter(String query) {
        this.query=query;
        diaryList.clear();
        if(query.isEmpty()) {
            diaryList.addAll(backUpDiaryList);
        } else {
            for (int i=0;i<backUpDiaryList.size(); i++){
                Diary obj = backUpDiaryList.get(i);
                if (obj.getTitle().toLowerCase().startsWith(query) ||obj.getDescription().toLowerCase().startsWith(query)) {
                    diaryList.add(obj);
                }

            }
        }
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public DiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiaryHolder(ItemDiaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public class DiaryHolder extends RecyclerView.ViewHolder {

        private ItemDiaryBinding binding;

        public DiaryHolder(ItemDiaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(int position) {
            Diary diary = diaryList.get(position);
            binding.tvDiaryDate.setText(diary.getDate());
            binding.tvDiaryTitle.setText(diary.getTitle());
            binding.tvDiaryDescription.setText(diary.getDescription());
            if (diary.getSaveType() == Constants.SAVE_TYPE_SAVED) {
                binding.tvDiarySaveType.setText("");
            } else {
                binding.tvDiarySaveType.setText("DRAFT");
            }

            List<Mood> moodList = DataSource.getAllMoods();
            for (int i = 0; i < moodList.size(); i++) {
                if (i == diary.getMoodId()) {
                    binding.ivDiaryMood.setImageResource(moodList.get(i).getMoodImage());
                }
            }

            itemView.setOnClickListener(view ->
                    onItemClickListener.onItemClick(null, itemView, position, 0));

        }

    }
}
