package com.example.islamicapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.islamicapp.model.SurahDetailsResponse;
import com.example.islamicapp.network.ApiClient;
import com.example.islamicapp.network.ApiInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SurahDetailsRepo {

    ApiInterface apiInterface;

    public SurahDetailsRepo() {
        apiInterface = ApiClient.getOverrideInstance().create(ApiInterface.class);
    }

    public LiveData<SurahDetailsResponse> getSurahDetails(String language, int id){
        MutableLiveData<SurahDetailsResponse> data = new MutableLiveData<>();
        Observable<SurahDetailsResponse> observable = apiInterface.getSurahDetails(language,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<SurahDetailsResponse> observer = new Observer<SurahDetailsResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull SurahDetailsResponse surahDetailsResponse) {
                data.setValue(surahDetailsResponse);

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    return data;
    }
}
