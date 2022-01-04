package com.muaz.mydiary.models;

import java.io.Serializable;

public class Tag implements Serializable {

    private int tagId;
    private String tag;
    private int tagDiaryId;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public Tag(int tagId, String tag) {
        this.tagId = tagId;
        this.tag = tag;
    }

    public Tag(int tagId, String tag, int tagDiaryId) {
        this.tagId = tagId;
        this.tag = tag;
        this.tagDiaryId = tagDiaryId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
