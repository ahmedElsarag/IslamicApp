package com.example.islamicapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.islamicapp.model.SurahResp;
import com.example.islamicapp.network.ApiClient;
import com.example.islamicapp.network.ApiInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SurahRepo {
    private static final String TAG = "surahRepo";
    ApiInterface apiInterface;

    public SurahRepo() {
        apiInterface = ApiClient.getInstance().create(ApiInterface.class);
    }

    public LiveData<SurahResp> getSurah() {
        MutableLiveData<SurahResp> data = new MutableLiveData<>();
        Observable observable = apiInterface.getSurah()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<SurahResp> observer = new Observer<SurahResp>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(SurahResp value) {
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
