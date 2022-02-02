package com.example.islamicapp.network;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit instance;

    public static Retrofit getInstance(){
        if(instance != null)
            instance=null;
            instance = new Retrofit.Builder()
                    .baseUrl("http://api.alquran.cloud/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

        return instance;
    }
    public static Retrofit getOverrideInstance(){
        if (instance!=null)
            instance=null;
        instance = new Retrofit.Builder()
                .baseUrl("https://quranenc.com/api/translation/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return instance;

    }

    public static Retrofit getPrayerRetrofitInstance(){
        if (instance!=null)
            instance=null;
        instance = new Retrofit.Builder()
                .baseUrl("http://api.aladhan.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return instance;

    }



}
