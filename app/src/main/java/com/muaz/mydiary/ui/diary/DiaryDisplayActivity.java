package com.muaz.mydiary.ui.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.muaz.mydiary.adapter.DiariesViewPagerAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.ActivityDiaryDisplayBinding;
import com.muaz.mydiary.models.Diary;

import java.util.List;

public class DiaryDisplayActivity extends AppCompatActivity {

    ActivityDiaryDisplayBinding binding;
    DbHelper dbHelper;
    DiariesViewPagerAdapter diariesViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiaryDisplayBinding.inflate(getLayoutInflater());
        setFullScreenFlags();
        setContentView(binding.getRoot());

        initUtils();

    }

    private void setFullScreenFlags() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initUtils() {
        dbHelper = new DbHelper(this);
        List<Diary> diaryList = dbHelper.getAllDiaries();
        diariesViewPagerAdapter = new DiariesViewPagerAdapter(diaryList);
        binding.vpDiaries.setAdapter(diariesViewPagerAdapter);
    }
}