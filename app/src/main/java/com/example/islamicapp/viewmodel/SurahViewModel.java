package com.example.islamicapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.islamicapp.model.SurahResp;
import com.example.islamicapp.repository.SurahRepo;

public class SurahViewModel extends ViewModel {
    private static final String TAG = "sviewmodel";
    private SurahRepo surahRepo;

    public SurahViewModel() {
        Log.d(TAG, "SurahViewModel: invoked");
        surahRepo = new SurahRepo();
    }
   public LiveData<SurahResp> getSurah(){

        return surahRepo.getSurah();
   }
}
