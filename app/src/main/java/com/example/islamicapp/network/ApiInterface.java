package com.example.islamicapp.network;

import com.example.islamicapp.model.PrayerTime;
import com.example.islamicapp.model.SurahDetails;
import com.example.islamicapp.model.SurahDetailsResponse;
import com.example.islamicapp.model.SurahResp;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("surah")
    public Observable<SurahResp> getSurah();

    @GET("sura/{language}/{id}")
    public Observable<SurahDetailsResponse> getSurahDetails(@Path("language") String language,
                                                            @Path("id") int surahId);

    //http://api.aladhan.com/v1/calendar?latitude=51.508515&longitude=-0.1254872&method=2&month=4&year=2017
    @GET("calendar")
    public Observable<PrayerTime> getPrayerTime(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("method") int method,
            @Query("month") String month,
            @Query("year") int year
    );
}
