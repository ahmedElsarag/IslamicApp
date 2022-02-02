package com.example.islamicapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.islamicapp.model.PrayerTime;
import com.example.islamicapp.model.SurahResp;
import com.example.islamicapp.network.ApiClient;
import com.example.islamicapp.network.ApiInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PrayerTimeRepo {

    private static final String TAG = "surahRepo";
    ApiInterface apiInterface;

    public PrayerTimeRepo() {
        apiInterface = ApiClient.getPrayerRetrofitInstance().create(ApiInterface.class);
    }

    public LiveData<PrayerTime> getPrayerTime(String lat, String lng, int method, String month, int year) {
        MutableLiveData<PrayerTime> data = new MutableLiveData<>();
        Observable observable = apiInterface.getPrayerTime(lat,lng,method,month,year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<PrayerTime> observer = new Observer<PrayerTime>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(PrayerTime value) {
                data.setValue(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

        return data;
    }
}
