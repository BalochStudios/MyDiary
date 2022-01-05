package com.muaz.mydiary.ui.diary;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.muaz.mydiary.databinding.ActivityImagesDisplayBinding;
import com.muaz.mydiary.utils.Constants;
import java.util.List;

public class ImagesDisplayActivity extends AppCompatActivity {

    ActivityImagesDisplayBinding binding;
    List<Bitmap> bitmapList;
    int bitmapPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImagesDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUtils();
    }

    private void initUtils() {
        bitmapList = new Gson().fromJson(getIntent().getStringExtra(Constants.INTENT_BITMAP_KEY),
                new TypeToken<List<Bitmap>>() {
                }.getType());
        bitmapPosition = getIntent().getIntExtra(Constants.INTENT_BITMAP_POSITION_KEY, 0);
    }
}