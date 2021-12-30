package com.muaz.mydiary.models;

import java.io.Serializable;

public class Color implements Serializable {

    private int colorId;
    private int colorResId;

    public Color() {
    }

    public Color(int colorId, int colorResId) {
        this.colorId = colorId;
        this.colorResId = colorResId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getColorResId() {
        return colorResId;
    }

    public void setColorResId(int colorResId) {
        this.colorResId = colorResId;
    }
}
