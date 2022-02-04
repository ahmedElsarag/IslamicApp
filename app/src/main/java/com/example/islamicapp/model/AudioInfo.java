package com.example.islamicapp.model;

public class AudioInfo {

    private String title;
    private String qarea;
    private int thumbnail;

    public AudioInfo(String title, String qarea, int thumbnail) {
        this.title = title;
        this.qarea = qarea;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQarea() {
        return qarea;
    }

    public void setQarea(String qarea) {
        this.qarea = qarea;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
