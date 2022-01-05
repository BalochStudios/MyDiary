package com.muaz.mydiary.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Diary implements Serializable {

    private Integer id;
    private String date;
    private String title;
    private String description;
    private int moodId;
    private int categoryId;
    private int backgroundId;
    private List<Bitmap> imageList;
    private int fontId;
    private int textSize;
    private int textDirection;
    private int textColorId;
    private List<Tag> tagsList;
    private int saveType;

    public Diary() {
    }

    public Diary(String date, String title, String description, int moodId, int categoryId, int backgroundId, List<Bitmap> imageList, int fontId, int textSize, int textDirection, int textColorId, List<Tag> tagsList, int saveType) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.moodId = moodId;
        this.categoryId = categoryId;
        this.backgroundId = backgroundId;
        this.imageList = imageList;
        this.fontId = fontId;
        this.textSize = textSize;
        this.textDirection = textDirection;
        this.textColorId = textColorId;
        this.tagsList = tagsList;
        this.saveType = saveType;
    }

    public Diary(Integer id, String date, String title, String description, int moodId, int categoryId, int backgroundId, List<Bitmap> imageList, int fontId, int textSize, int textDirection, int textColorId, List<Tag> tagsList, int saveType) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.moodId = moodId;
        this.categoryId = categoryId;
        this.backgroundId = backgroundId;
        this.imageList = imageList;
        this.fontId = fontId;
        this.textSize = textSize;
        this.textDirection = textDirection;
        this.textColorId = textColorId;
        this.tagsList = tagsList;
        this.saveType = saveType;
    }

    public int getSaveType() {
        return saveType;
    }

    public void setSaveType(int saveType) {
        this.saveType = saveType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMoodId() {
        return moodId;
    }

    public void setMoodId(int moodId) {
        this.moodId = moodId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public List<Bitmap> getImageList() {
        return imageList;
    }

    public void setImageList(List<Bitmap> imageList) {
        this.imageList = imageList;
    }

    public int getFontId() {
        return fontId;
    }

    public void setFontId(int fontId) {
        this.fontId = fontId;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextDirection() {
        return textDirection;
    }

    public void setTextDirection(int textDirection) {
        this.textDirection = textDirection;
    }

    public int getTextColorId() {
        return textColorId;
    }

    public void setTextColorId(int textColorId) {
        this.textColorId = textColorId;
    }

    public List<Tag> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tag> tagsList) {
        this.tagsList = tagsList;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
