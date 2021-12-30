package com.muaz.mydiary.utils;

import com.muaz.mydiary.R;
import com.muaz.mydiary.models.Mood;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static List<Mood> getAllMoods() {
        ArrayList<Mood> moodArrayList = new ArrayList<>();
        moodArrayList.add(new Mood(R.drawable.ic_smiling, false));
        moodArrayList.add(new Mood(R.drawable.ic_angry, false));
        moodArrayList.add(new Mood(R.drawable.ic_crying, false));
        moodArrayList.add(new Mood(R.drawable.ic_blessed, false));
        moodArrayList.add(new Mood(R.drawable.ic_tired, false));
        return moodArrayList;
    }
}
