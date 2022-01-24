package com.example.islamicapp.model;

import com.google.gson.annotations.SerializedName;

public class SurahDetails {
    @SerializedName("id")
    public int id;
    @SerializedName("sura")
    public int sura;
    @SerializedName("aya")
    public int aya;
    @SerializedName("arabic_text")
    public String arabic_text;
    @SerializedName("translation")
    public String translation;
    @SerializedName("footnotes")
    public String footnotes;

    public SurahDetails(int id, int sura, int aya, String arabic_text, String translation, String footnotes) {
        this.id = id;
        this.sura = sura;
        this.aya = aya;
        this.arabic_text = arabic_text;
        this.translation = translation;
        this.footnotes = footnotes;
    }


}


