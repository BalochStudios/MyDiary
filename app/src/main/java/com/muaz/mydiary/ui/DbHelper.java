package com.muaz.mydiary.ui;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TABLE_DIARY = "tbl_diary";
    private static final String KEY_DIARY_ID = "id";
    private static final String KEY_DIARY_DATE = "date";
    private static final String KEY_DIARY_TITLE = "title";
    private static final String KEY_DIARY_DESCRIPTION = "description";
    private static final String KEY_DIARY_BACKGROUND = "background";
    private static final String KEY_DIARY_IMAGE = "image";
    private static final String KEY_DIARY_VIDEO = "video";
    private static final String KEY_DIARY_ICON = "icon";
    private static final String KEY_DIARY_STICKER = "sticker";
    private static final String KEY_DIARY_FONT = "font";
    private static final String KEY_DIARY_LIST = "list";
    private static final String KEY_DIARY_DESIGN = "design";
    private static final String KEY_DIARY_AUDIO = "audio";
    private static final String KEY_DIARY_PAINTING = "painting";


    private static final String CREATE_TABLE_DIARY = "CREATE TABLE " + TABLE_DIARY + "( " +
            KEY_DIARY_ID + " INTEGER PRIMARY KEY," +
            KEY_DIARY_DATE + " TEXT," +
            KEY_DIARY_TITLE + " TEXT," +
            KEY_DIARY_DESCRIPTION + " TEXT," +
            KEY_DIARY_BACKGROUND + " TEXT," +
            KEY_DIARY_IMAGE + " TEXT," +
            KEY_DIARY_VIDEO + " TEXT," +
            KEY_DIARY_ICON + " TEXT," +
            KEY_DIARY_STICKER + " TEXT," +
            KEY_DIARY_FONT + " TEXT," +
            KEY_DIARY_LIST + " TEXT," +
            KEY_DIARY_DESIGN + " TEXT," +
            KEY_DIARY_AUDIO + " TEXT," +
            KEY_DIARY_PAINTING + " TEXT" +
            ")";

    public DbHelper(@Nullable Context context) {
        super(context, "db.diary", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_DIARY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP  TABLE IF EXISTS " + TABLE_DIARY);
        onCreate(sqLiteDatabase);

    }

    public long addToDiary(Diary diary) {
        if (isInDiary(diary)) {
            return -1;
        } else {
            ContentValues cv = new ContentValues();
            cv.put(KEY_DIARY_ID, diary.getId());
            cv.put(KEY_DIARY_DATE, diary.getDate());
            cv.put(KEY_DIARY_TITLE, diary.getTitle());
            cv.put(KEY_DIARY_DESCRIPTION, diary.getDescription());
            cv.put(KEY_DIARY_BACKGROUND, diary.getBackground());
            cv.put(KEY_DIARY_IMAGE, diary.getImage());
            cv.put(KEY_DIARY_VIDEO, diary.getVideo());
            cv.put(KEY_DIARY_ICON, diary.getIcon());
            cv.put(KEY_DIARY_STICKER, diary.getSticker());
            cv.put(KEY_DIARY_FONT, diary.getFont());
            cv.put(KEY_DIARY_LIST, diary.getList());
            cv.put(KEY_DIARY_DESIGN, diary.getDesign());
            cv.put(KEY_DIARY_AUDIO, diary.getAudio());
            cv.put(KEY_DIARY_PAINTING, diary.getPainting());
            SQLiteDatabase db = getWritableDatabase();
            return db.insert(TABLE_DIARY, null, cv);
        }


    }

    public int deleteInDiary(Diary diary) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_DIARY, KEY_DIARY_ID + " = " + diary.getId(), null);
    }

    public int deleteAllRecord() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_DIARY, "1", null);
    }

    public boolean isInDiary(Diary diary) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_DIARY + " Where " + KEY_DIARY_ID + " = " + diary.getId(), null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<Diary> AllDiaryRecord() {
        ArrayList<Diary> diaryList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_DIARY, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex(KEY_DIARY_ID));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(KEY_DIARY_DATE));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(KEY_DIARY_TITLE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(KEY_DIARY_DESCRIPTION));
            @SuppressLint("Range") String background = cursor.getString(cursor.getColumnIndex(KEY_DIARY_BACKGROUND));
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(KEY_DIARY_IMAGE));
            @SuppressLint("Range") String video = cursor.getString(cursor.getColumnIndex(KEY_DIARY_VIDEO));
            @SuppressLint("Range") String icon = cursor.getString(cursor.getColumnIndex(KEY_DIARY_ICON));
            @SuppressLint("Range") String sticker = cursor.getString(cursor.getColumnIndex(KEY_DIARY_STICKER));
            @SuppressLint("Range") String font = cursor.getString(cursor.getColumnIndex(KEY_DIARY_FONT));
            @SuppressLint("Range") String list = cursor.getString(cursor.getColumnIndex(KEY_DIARY_LIST));
            @SuppressLint("Range") String design = cursor.getString(cursor.getColumnIndex(KEY_DIARY_DESIGN));
            @SuppressLint("Range") String audio = cursor.getString(cursor.getColumnIndex(KEY_DIARY_AUDIO));
            @SuppressLint("Range") String painting = cursor.getString(cursor.getColumnIndex(KEY_DIARY_PAINTING));

            Diary diary = new Diary(id, date, title, description, background, image, video, icon, sticker, font, list, design, audio, painting);
            diaryList.add(diary);

        }


        return diaryList;
    }


}
