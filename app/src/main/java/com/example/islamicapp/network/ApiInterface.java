package com.example.islamicapp.network;

import com.example.islamicapp.model.SurahDetails;
import com.example.islamicapp.model.SurahDetailsResponse;
import com.example.islamicapp.model.SurahResp;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("surah")
    public Observable<SurahResp> getSurah();

    @GET("sura/{language}/{id}")
    public Observable<SurahDetailsResponse> getSurahDetails(@Path("language") String language,
                                                            @Path("id") int surahId);
}
