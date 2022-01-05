package com.muaz.mydiary.models;

public class DiaryCategory {

    private int categoryImage;
    private String categoryName;
    private boolean selectedCategory;

    public DiaryCategory(int categoryImage, String categoryName) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.selectedCategory = false;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(boolean selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
