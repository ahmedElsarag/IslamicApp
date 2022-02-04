package com.example.islamicapp;

import static com.example.islamicapp.ApplicationClass.ACTION_NEXT;
import static com.example.islamicapp.ApplicationClass.ACTION_PLAY;
import static com.example.islamicapp.ApplicationClass.ACTION_PREV;
import static com.example.islamicapp.ApplicationClass.CHANNEL_ID_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islamicapp.model.AudioInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener, ActionPlaying, ServiceConnection {

    ImageView play, next, prev;
    TextView title;
    List<AudioInfo> audio = new ArrayList<>();
    int position = 0;
    boolean isPlaying = false;
    AudioService audioService;
    MediaSessionCompat mediaSession;
    MediaPlayer   mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        title = findViewById(R.id.title);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);

        mediaSession = new MediaSessionCompat(this,"playerAudio");
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, this, BIND_AUTO_CREATE);

        pubulateFiles();
        title.setText(audio.get(position).getTitle());

        String uri = "https://download.quranicaudio.com/quran/abdul_basit_murattal/001.mp3";
        mediaPlayer = MediaPlayer.create(MainActivity2.this, Uri.parse(uri));
    }

    private void pubulateFiles() {
        audio.add(new AudioInfo("alfateha", "mashary", R.drawable.al3fasi));
        audio.add(new AudioInfo("albakara", "nasr", R.drawable.naser));
        audio.add(new AudioInfo("alnas", "abdelbaset", R.drawable.abdelbaset));
        audio.add(new AudioInfo("mariam", "fares", R.drawable.fares));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                playClicked();
                break;
            case R.id.next:
                nextClicked();
                break;
            case R.id.prev:
                prevClicked();
                break;
        }
    }

    @Override
    public void playClicked() {
        if (!isPlaying) {
            isPlaying = true;
            play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            showNotification(R.drawable.ic_baseline_pause_circle_filled_24);
            mediaPlayer.start();
        } else {
            isPlaying = false;
            play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            showNotification(R.drawable.ic_baseline_play_circle_filled_24);
            mediaPlayer.pause();

        }

    }

    @Override
    public void nextClicked() {
        if (position == 3)
            position = 0;
        else
            position++;
        title.setText(audio.get(position).getTitle());
        if (!isPlaying) {
            showNotification(R.drawable.ic_baseline_play_circle_filled_24);
        } else {
            showNotification(R.drawable.ic_baseline_pause_circle_filled_24);
        }

    }

    @Override
    public void prevClicked() {
        if (position == 0)
            position = 3;
        else
            position--;
        title.setText(audio.get(position).getTitle());

        if (!isPlaying) {
            showNotification(R.drawable.ic_baseline_play_circle_filled_24);
        } else {
            showNotification(R.drawable.ic_baseline_pause_circle_filled_24);
        }

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        AudioService.MyBinder binder = (AudioService.MyBinder) iBinder;
        audioService = binder.getService();
        audioService.setCallback(MainActivity2.this);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        audioService = null;

    }

    public void showNotification(int playPauseBtn) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, intent, 0);
        Intent prevIntent = new Intent(this, NotificationReceiver.class).setAction(ACTION_PREV);
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this, 0, prevIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Intent playIntent = new Intent(this, NotificationReceiver.class).setAction(ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Intent nextIntent = new Intent(this, NotificationReceiver.class).setAction(ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap picture = BitmapFactory.decodeResource(getResources(),
                audio.get(position).getThumbnail());
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_2)
                .setSmallIcon(audio.get(position).getThumbnail())
                .setLargeIcon(picture)
                .setContentTitle(audio.get(position).getTitle())
                .setContentText(audio.get(position).getQarea())
                .addAction(R.drawable.ic_baseline_skip_previous_24,
                        "Previous", prevPendingIntent)
                .addAction(playPauseBtn, "Play", playPendingIntent)
                .addAction(R.drawable.ic_baseline_skip_next_24, "Next", nextPendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);

    }
}