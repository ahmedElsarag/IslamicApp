package com.example.islamicapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.islamicapp.model.SurahDetailsResponse;
import com.example.islamicapp.repository.SurahDetailsRepo;

public class SurahDetailsViewModel extends ViewModel {
    SurahDetailsRepo surahDetailsRepo;
    public SurahDetailsViewModel(){
        surahDetailsRepo = new SurahDetailsRepo();
    }
    public LiveData<SurahDetailsResponse> getSurahDetails(String lang, int id){
        return surahDetailsRepo.getSurahDetails(lang,id);
    }
}
