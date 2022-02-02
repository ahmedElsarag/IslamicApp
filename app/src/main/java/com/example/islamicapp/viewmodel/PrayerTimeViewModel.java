package com.example.islamicapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.islamicapp.model.PrayerTime;
import com.example.islamicapp.model.SurahResp;
import com.example.islamicapp.repository.PrayerTimeRepo;
import com.example.islamicapp.repository.SurahRepo;

public class PrayerTimeViewModel extends ViewModel {

    private static final String TAG = "sviewmodel";
    private PrayerTimeRepo prayerTimeRepo;

    public PrayerTimeViewModel() {
        Log.d(TAG, "SurahViewModel: invoked");
        prayerTimeRepo = new PrayerTimeRepo();
    }
    public LiveData<PrayerTime> getPrayerTime(String lat, String lng, int method, String month, int year){

        return prayerTimeRepo.getPrayerTime(lat,lng,method,month,year);
    }

}
