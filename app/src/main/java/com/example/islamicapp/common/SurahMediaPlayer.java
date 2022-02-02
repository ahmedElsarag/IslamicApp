package com.example.islamicapp.common;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.widget.SeekBar;

import com.example.islamicapp.Variables;

import java.util.concurrent.TimeUnit;


public class SurahMediaPlayer {

    public MediaPlayer mediaPlayer = null;
    static SurahMediaPlayer instance = null;
    Context context;
    Runnable runnable = null;
    Handler handler = new Handler();
    Variables variables = new Variables();
    private static final String TAG = "mediap";

    public SurahMediaPlayer(Context context) {
        this.context = context;
    }

    public void initMediaPlayer(SeekBar seekBar, String qarea, String id) {
        String uri = "https://download.quranicaudio.com/quran/" + qarea + "/" + id + ".mp3";

        mediaPlayer = getPlayerInstance(uri,id);
        runnable = getRunnableInstance(seekBar);

    }

    private Runnable getRunnableInstance(SeekBar seekBar) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(runnable, 500);
                }
            };
        return runnable;
    }

    private MediaPlayer getPlayerInstance(String uri,String id) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(uri));
            variables.audioId = Integer.parseInt(id);
            //when you play audio in surah then return and play audio in another surah
        } else if (variables.audioId != Integer.parseInt(id)){
            mediaPlayer.stop();
          //  handler.removeCallbacks(runnable);
            mediaPlayer = MediaPlayer.create(context, Uri.parse(uri));
            variables.audioId = Integer.parseInt(id);
        }
        return mediaPlayer;
    }

    public void readerChange(){
        mediaPlayer.stop();
       handler.removeCallbacks(runnable);
        mediaPlayer = null;
    }

    public static SurahMediaPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new SurahMediaPlayer(context);
        }
        return instance;
    }


    public String getDuration() {
        int duration = mediaPlayer.getDuration();
        return timeFormat(duration);
    }

    public String timeFormat(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    public String getCurrentPosition() {
        return timeFormat(mediaPlayer.getCurrentPosition());
    }

    public void playMediaPlayer(SeekBar seekBar) {
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        handler.postDelayed(runnable, 0);

    }

    public void pauseMediaPlayer() {
        mediaPlayer.pause();
        handler.removeCallbacks(runnable);
    }

    public void onSeekbarDraged(int progress) {
       mediaPlayer.seekTo(progress);
    }

    public void onComplete() {
        handler.removeCallbacks(runnable);
    }
}
