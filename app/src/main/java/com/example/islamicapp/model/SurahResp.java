package com.example.islamicapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurahResp {
    @SerializedName("data")
    List<Surah> data;

    public List<Surah> getData() {
        return data;
    }

    public void setData(List<Surah> data) {
        this.data = data;
    }
}
