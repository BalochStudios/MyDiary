package com.muaz.mydiary.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "diary.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_DIARY = "tbl_diary";
    private static final String TABLE_DIARY_IMAGES = "tbl_diary_images";

    private static final String KEY_DIARY_ID = "id";
    private static final String KEY_DIARY_DATE = "date";
    private static final String KEY_DIARY_TITLE = "title";
    private static final String KEY_DIARY_DESCRIPTION = "description";
    private static final String KEY_DIARY_BACKGROUND_ID = "backgroundId";
    private static final String KEY_DIARY_MOOD_ID = "moodId";
    private static final String KEY_DIARY_FONT_ID = "fontId";
    private static final String KEY_DIARY_TAGS = "tags";
    private static final String KEY_DIARY_TEXT_SIZE = "textSize";
    private static final String KEY_DIARY_TEXT_DIRECTION = "textDirection";
    private static final String KEY_DIARY_TEXT_COLOR_ID = "textColorId";
    private static final String KEY_DIARY_SAVE_TYPE = "saveType";

    private static final String KEY_DIARY_IMAGE_ID = "imageId";
    private static final String KEY_DIARY_IMAGE = "image";
    private static final String KEY_DIARY_IMAGE_DIARY_ID = "imageDiaryId";

    private static final String CREATE_TABLE_DIARY_QUERY = "CREATE TABLE " + TABLE_DIARY + "( " +
            KEY_DIARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_DIARY_DATE + " TEXT," +
            KEY_DIARY_TITLE + " TEXT," +
            KEY_DIARY_DESCRIPTION + " TEXT," +
            KEY_DIARY_BACKGROUND_ID + " INTEGER," +
            KEY_DIARY_FONT_ID + " TEXT," +
            KEY_DIARY_MOOD_ID + " INTEGER," +
            KEY_DIARY_TAGS + " TEXT," +
            KEY_DIARY_TEXT_SIZE + " INTEGER," +
            KEY_DIARY_TEXT_DIRECTION + " INTEGER," +
            KEY_DIARY_TEXT_COLOR_ID + " INTEGER," +
            KEY_DIARY_SAVE_TYPE + " INTEGER" +
            ")";

    private final String CREATE_TABLE_DIARY_IMAGES_QUERY = "CREATE TABLE " + TABLE_DIARY_IMAGES + "( " +
            KEY_DIARY_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_DIARY_IMAGE + " BLOB," +
            KEY_DIARY_IMAGE_DIARY_ID + " INTEGER)";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_DIARY_QUERY);
        sqLiteDatabase.execSQL(CREATE_TABLE_DIARY_IMAGES_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY_IMAGES);
        onCreate(sqLiteDatabase);
    }

    public long addToDiary(Diary diary) {
        if (isInDiary(diary)) {
            return -1;
        } else {
            ContentValues cv = new ContentValues();
            cv.put(KEY_DIARY_DATE, diary.getDate());
            cv.put(KEY_DIARY_TITLE, diary.getTitle());
            cv.put(KEY_DIARY_DESCRIPTION, diary.getDescription());
            cv.put(KEY_DIARY_BACKGROUND_ID, diary.getBackgroundId());
            cv.put(KEY_DIARY_MOOD_ID, diary.getMoodId());
            cv.put(KEY_DIARY_FONT_ID, diary.getFontId());
            cv.put(KEY_DIARY_TAGS, new Gson().toJson(diary.getTagsList()));
            cv.put(KEY_DIARY_TEXT_SIZE, diary.getTextSize());
            cv.put(KEY_DIARY_TEXT_DIRECTION, diary.getTextDirection());
            cv.put(KEY_DIARY_TEXT_COLOR_ID, diary.getTextColorId());
            cv.put(KEY_DIARY_SAVE_TYPE, diary.getSaveType());


            SQLiteDatabase db = getWritableDatabase();
            long dairyId = db.insert(TABLE_DIARY, null, cv);
            if (diary.getImageList().size() > 0)
                addDiaryImages(diary.getImageList(), dairyId);

            return dairyId;
        }
    }

    private void addDiaryImages(List<Bitmap> imageList, long diaryId) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        for (Bitmap bitmap : imageList) {
            cv.put(KEY_DIARY_IMAGE, UtilityFunctions.getBytes(bitmap));
            cv.put(KEY_DIARY_IMAGE_DIARY_ID, diaryId);

            db.insert(TABLE_DIARY_IMAGES, null, cv);
        }
    }

    public int deleteDiary(Diary diary) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_DIARY, KEY_DIARY_ID + "=" + diary.getId(), null);
    }

    public int deleteAllRecord() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_DIARY, "1", null);
    }

    public boolean isInDiary(Diary diary) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DIARY + " WHERE " + KEY_DIARY_ID + " = " + diary.getId(), null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Diary> getAllDiaries() {
        ArrayList<Diary> diaryList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DIARY + " ORDER BY " + KEY_DIARY_ID + " DESC", null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_ID));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(KEY_DIARY_DATE));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(KEY_DIARY_TITLE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(KEY_DIARY_DESCRIPTION));
            @SuppressLint("Range") int moodId = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_MOOD_ID));
            @SuppressLint("Range") int backgroundId = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_BACKGROUND_ID));
            @SuppressLint("Range") int fontId = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_FONT_ID));
            @SuppressLint("Range") int textSize = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_TEXT_SIZE));
            @SuppressLint("Range") int colorId = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_TEXT_COLOR_ID));
            @SuppressLint("Range") int textDirection = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_TEXT_DIRECTION));
            @SuppressLint("Range") String tags = cursor.getString(cursor.getColumnIndex(KEY_DIARY_TAGS));
            @SuppressLint("Range") int saveType = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_SAVE_TYPE));

            List<Bitmap> bitmapList = gtDiaryImages(id);
            Diary diary = new Diary(id,
                    date,
                    title,
                    description,
                    moodId,
                    backgroundId,
                    bitmapList,
                    fontId,
                    textSize,
                    textDirection,
                    colorId,
                    new Gson().fromJson(tags, new TypeToken<List<String>>() {
                    }.getType()),
                    saveType);
            diaryList.add(diary);

        }

        cursor.close();
        return diaryList;
    }

    private List<Bitmap> gtDiaryImages(Integer id) {
        ArrayList<Bitmap> bitmapList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DIARY_IMAGES + " WHERE " + KEY_DIARY_IMAGE_DIARY_ID + "=" + id;

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(KEY_DIARY_IMAGE));
            bitmapList.add(UtilityFunctions.getImage(image));
        }
        cursor.close();
        return bitmapList;
    }


}
