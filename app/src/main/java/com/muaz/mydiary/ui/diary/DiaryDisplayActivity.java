package com.muaz.mydiary.ui.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import com.muaz.mydiary.adapter.DiariesViewPagerAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.ActivityDiaryDisplayBinding;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.utils.Constants;

import java.util.List;

public class DiaryDisplayActivity extends AppCompatActivity {

    ActivityDiaryDisplayBinding binding;
    DbHelper dbHelper;
    DiariesViewPagerAdapter diariesViewPagerAdapter;
    int selectedPosition;

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
        selectedPosition = getIntent().getIntExtra(Constants.INTENT_SELECTED_DIARY_POSITION, 0);
        dbHelper = new DbHelper(this);
        List<Diary> diaryList = dbHelper.getAllDiaries();
        diariesViewPagerAdapter = new DiariesViewPagerAdapter(diaryList);
        binding.vpDiaries.setAdapter(diariesViewPagerAdapter);
        new Handler(Looper.getMainLooper()).postDelayed(() -> binding.vpDiaries.setCurrentItem(selectedPosition, false), 5);

    }
}