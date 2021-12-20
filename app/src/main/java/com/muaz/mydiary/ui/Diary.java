package com.muaz.mydiary.ui;

public class Diary {
    private Integer id;
    private String date;
    private String title;
    private String description;
    private String background;
    private String image;
    private String video;
    private String icon;
    private String sticker;
    private String font;
    private String list;
    private String design;
    private String audio;
    private String painting;

    public Diary() {
    }

    public Diary(Integer id, String date, String title, String description, String background, String image, String video, String icon, String sticker, String font, String list, String design, String audio, String painting) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.background = background;
        this.image = image;
        this.video = video;
        this.icon = icon;
        this.sticker = sticker;
        this.font = font;
        this.list = list;
        this.design = design;
        this.audio = audio;
        this.painting = painting;
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

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSticker() {
        return sticker;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPainting() {
        return painting;
    }

    public void setPainting(String painting) {
        this.painting = painting;
    }
}
