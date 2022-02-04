package com.example.islamicapp;

import static com.example.islamicapp.ApplicationClass.ACTION_NEXT;
import static com.example.islamicapp.ApplicationClass.ACTION_PLAY;
import static com.example.islamicapp.ApplicationClass.ACTION_PREV;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.Provider;

public class AudioService extends Service {
    IBinder mBinder = new MyBinder();
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PREV = "PREVIOUS";
    public static final String ACTION_PLAY = "PLAY";
    ActionPlaying actionPlaying;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder{
        AudioService getService(){
            return AudioService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionName = intent.getStringExtra("myActionName");
        if (actionName != null){
            switch (actionName){
                case ACTION_PLAY:
                    if (actionPlaying != null){
                        actionPlaying.playClicked();
                    }
                    break;
                case ACTION_NEXT:
                    if (actionPlaying != null){
                        actionPlaying.nextClicked();
                    }
                    break;
                case ACTION_PREV:
                    if (actionPlaying != null){
                        actionPlaying.prevClicked();
                    }
                    break;
            }
        }

        return START_STICKY;
    }
    public void setCallback(ActionPlaying actionPlaying){
        this.actionPlaying = actionPlaying;

    }
}
