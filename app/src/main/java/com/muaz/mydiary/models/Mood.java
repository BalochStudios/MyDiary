package com.muaz.mydiary.models;

import java.io.Serializable;

public class Mood implements Serializable {

    private int moodImage;
    private boolean isSelected;
    private boolean selectedEmoji;

    public Mood(int moodImage, boolean isSelected) {
        this.moodImage = moodImage;
        this.isSelected = isSelected;
        this.selectedEmoji = false;
    }

    public boolean isSelectedEmoji() {
        return selectedEmoji;
    }

    public void setSelectedEmoji(boolean selectedEmoji) {
        this.selectedEmoji = selectedEmoji;
    }

    public int getMoodImage() {
        return moodImage;
    }

    public void setMoodImage(int moodImage) {
        this.moodImage = moodImage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
