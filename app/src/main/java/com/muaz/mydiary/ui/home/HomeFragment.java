package com.muaz.mydiary.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.R;
import com.muaz.mydiary.adapter.DiaryAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.FragmentHomeBinding;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.ui.diary.AddDiaryActivity;
import com.muaz.mydiary.ui.diary.DiaryDisplayActivity;
import com.muaz.mydiary.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<Diary> selectedDiaries;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDiaryRV();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivAddNewDiary.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AddDiaryActivity.class)));
        setDiaryRV();
    }

    private void setDiaryRV() {
        DbHelper dbHelper = new DbHelper(getContext());
        selectedDiaries = new ArrayList<>();
        List<Diary> diaryList = dbHelper.getAllDiaries();
        DiaryAdapter diaryAdapter = new DiaryAdapter(diaryList, (adapterView, view, i, l) -> {
            Intent intent = new Intent(getContext(), DiaryDisplayActivity.class);
            intent.putExtra(Constants.INTENT_SELECTED_DIARY_POSITION, i);
            startActivity(intent);
        });
        binding.rvDiary.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvDiary.setAdapter(diaryAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}