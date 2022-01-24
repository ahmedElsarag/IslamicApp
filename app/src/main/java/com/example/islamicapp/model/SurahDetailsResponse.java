package com.example.islamicapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SurahDetailsResponse{
    @SerializedName("result")
    public ArrayList<SurahDetails> result;

    public ArrayList<SurahDetails> getResult() {
        return result;
    }

    public void setResult(ArrayList<SurahDetails> result) {
        this.result = result;
    }
}
