package com.muaz.mydiary.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.adapter.DiaryAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


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
        DiaryAdapter diaryAdapter = new DiaryAdapter(dbHelper.getAllDiaries(), (adapterView, view, i, l) -> {

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