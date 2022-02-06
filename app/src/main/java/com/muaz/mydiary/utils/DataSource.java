package com.muaz.mydiary.utils;

import android.content.Context;

import com.muaz.mydiary.R;
import com.muaz.mydiary.models.Color;
import com.muaz.mydiary.models.DiaryCategory;
import com.muaz.mydiary.models.Mood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSource {
    public static List<Mood> getAllMoods() {
        List<Mood> moodArrayList = new ArrayList<>();
        moodArrayList.add(new Mood(R.drawable.ic_smiling, false));
        moodArrayList.add(new Mood(R.drawable.ic_angry, false));
        moodArrayList.add(new Mood(R.drawable.ic_crying, false));
        moodArrayList.add(new Mood(R.drawable.ic_blessed, false));
        moodArrayList.add(new Mood(R.drawable.ic_tired, false));
        return moodArrayList;
    }

    public static List<DiaryCategory> getAllCategories() {
        List<DiaryCategory> diaryCategories = new ArrayList<>();
        diaryCategories.add(new DiaryCategory(R.drawable.ic_family, "Family"));
        diaryCategories.add(new DiaryCategory(R.drawable.ic_love, "Love"));
        diaryCategories.add(new DiaryCategory(R.drawable.ic_friends, "Friends"));
        diaryCategories.add(new DiaryCategory(R.drawable.ic_myself, "MySelf"));
        diaryCategories.add(new DiaryCategory(R.drawable.ic_pet, "Pet"));
        return diaryCategories;
    }

    public static List<Mood> getAllBitmaps() {
        ArrayList<Mood> backgrounds = new ArrayList<>();
        backgrounds.add(new Mood(R.drawable.bg_1, false));
        backgrounds.add(new Mood(R.drawable.bg_2, false));
        backgrounds.add(new Mood(R.drawable.bg_3, false));
        backgrounds.add(new Mood(R.drawable.bg_4, false));
        backgrounds.add(new Mood(R.drawable.bg_5, false));
        backgrounds.add(new Mood(R.drawable.bg_6, false));
        backgrounds.add(new Mood(R.drawable.bg_7, false));
        backgrounds.add(new Mood(R.drawable.bg_8, false));
        backgrounds.add(new Mood(R.drawable.bg_9, false));
        backgrounds.add(new Mood(R.drawable.bg_10, false));
        return backgrounds;
    }

    public static List<String> getAllFonts(Context context) {
        List<String> fontsList = new ArrayList<>();
        try {
            fontsList = Arrays.asList(context.getAssets().list("fonts"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontsList;
    }

    public static List<Color> getAllColors() {
        ArrayList<Color> colorsList = new ArrayList<>();
        colorsList.add(new Color(1, R.color.color_black));
        colorsList.add(new Color(2, R.color.color_blue));
        colorsList.add(new Color(3, R.color.color_brown));
        colorsList.add(new Color(4, R.color.color_green));
        colorsList.add(new Color(5, R.color.color_white));
        colorsList.add(new Color(6, R.color.color_pink));
        colorsList.add(new Color(7, R.color.color_red));
        colorsList.add(new Color(8, R.color.color_yellow));
        return colorsList;
    }
}
