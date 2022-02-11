package com.example.islamicapp.ui;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.IBinder;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.islamicapp.AudioPlayerService;
import com.example.islamicapp.R;
import com.example.islamicapp.adapter.SuraDetailsAdapter;
import com.example.islamicapp.Variables;
import com.example.islamicapp.databinding.FragmentSurahDetailsBinding;
import com.example.islamicapp.model.SurahDetails;
import com.example.islamicapp.viewmodel.SurahDetailsViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailsFragment extends Fragment implements View.OnClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {
    FragmentSurahDetailsBinding binding;
    SurahDetailsViewModel surahDetailsViewModel;
    List<SurahDetails> list;
    SuraDetailsAdapter adapter;
    SharedPreferences prefs;
    String qreaa;
    private static final String TAG = "fragmsurah";
    //-----------------------------------------------------
    private boolean mBound = false;
    private AudioPlayerService audioPlayerService;
    private static final int READ_PHONE_STATE_REQUEST_CODE = 22;
    private BroadcastReceiver broadcastReceiver;
    private AudioManager audio;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            AudioPlayerService.AudioPlayerBinder mServiceBinder = (AudioPlayerService.AudioPlayerBinder) iBinder;
            audioPlayerService = mServiceBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.exit(0);
        }
    };


    public SurahDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSurahDetailsBinding.inflate(getLayoutInflater());

        initViews();
        //get surah data from api
        prefs = getContext().getSharedPreferences(Variables.SHARED_PREFERANCE_NAME, Context.MODE_PRIVATE);
        String translationLanguage = prefs.getString(Variables.LANGUAGE, Variables.ENGLISH);
        qreaa = prefs.getString(Variables.READER, Variables.readerName);
        Variables.readerName = qreaa;
        getData(translationLanguage, Variables.position);
        prefs.registerOnSharedPreferenceChangeListener(this);
        binding.surahName.setText(Variables.surahName);
        //------------------------

        processPhoneListenerPermission();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                if (tm != null) {
                    if (tm.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
                        if (audioPlayerService.isPlaying()) {
                            audioPlayerService.stop();
                            binding.playStopBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                        }
                    }
                }

                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                    if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
//                        if (snackbar.isShown()) {
//                            snackbar.dismiss();
//                        }
                        binding.playStopBtn.setEnabled(true);
                    } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                        binding.playStopBtn.setEnabled(false);
                    }
                }

                //recive the intent value that passed from the AudioPlayerService using prodcats and intent in line 52

                int playerState = intent.getIntExtra("state", 0);
                if (playerState == PlaybackStateCompat.STATE_BUFFERING) {
                    binding.animationView.setVisibility(View.GONE);
                    binding.animationPuase.setVisibility(View.VISIBLE);
                    binding.playStopBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    Variables.paused=false;
                } else if (playerState == PlaybackStateCompat.STATE_PLAYING) {
                    binding.playStopBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    binding.animationView.setVisibility(View.VISIBLE);
                    binding.animationPuase.setVisibility(View.GONE);
                    Variables.paused = false;
                    int musicVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (musicVolume == 0) {
                        Toast.makeText(getContext(), "Volume is muted", Toast.LENGTH_LONG).show();
                    }
                } else if (playerState == PlaybackStateCompat.STATE_PAUSED) {
                    binding.playStopBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                    binding.animationView.setVisibility(View.GONE);
                    binding.animationPuase.setVisibility(View.VISIBLE);
                    Variables.paused = true;
                }else if (playerState == PlaybackStateCompat.STATE_FAST_FORWARDING){
                    Log.d(TAG, "onReceive: end");
                    binding.playStopBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                    binding.animationView.setVisibility(View.GONE);
                    binding.animationPuase.setVisibility(View.VISIBLE);
                    Variables.paused = false;
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(broadcastReceiver, filter);

        audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        return binding.getRoot();
    }


    //------------------------------------------------------------------------

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), AudioPlayerService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((broadcastReceiver),
                new IntentFilter("com.example.exoplayer.PLAYER_STATUS")
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (audioPlayerService != null && audioPlayerService.isPlaying()) {
            binding.playStopBtn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            binding.animationView.setVisibility(View.VISIBLE);
            binding.animationPuase.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "activity destroy called");
//        if (mBound) {
//            unbindService(serviceConnection);
//            mBound = false;
//        }
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        Log.i(TAG, "activity destroyed");
        super.onDestroy();
    }
    //---------------------------------------------------------------------

    ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle(getResources().getString(R.string.perm_dialog_title))
                            .setMessage(getResources().getString(R.string.perm_dialog_body))
                            .setNegativeButton(getResources().getString(R.string.negative_btn), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).setPositiveButton(getResources().getString(R.string.positive_btn), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // This is Case 3. Request for permission here
                            //if user denied permission twice android will block request permession thi from android11
                            if (android.os.Build.VERSION.SDK_INT >= 11) {
                                requestPermissionLauncher.launch(Manifest.permission.READ_PHONE_STATE);
                                Toast.makeText(getContext(), getResources().getString(R.string.deny_perm_twice)
                                        , Toast.LENGTH_LONG).show();
                            }

                        }
                    })
                            .show();

                }
            });

    private void processPhoneListenerPermission() {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            requestPermissionLauncher.launch(Manifest.permission.READ_PHONE_STATE);
        }
    }

    public void playStop(String qreaa) {
        Variables.currentAudioId = Variables.position;
        String streamUrl = "https://download.quranicaudio.com/quran/" + qreaa + "/" + getAudioId(Variables.position + 1) + ".mp3";
        Log.d(TAG, "playStop: " + audioPlayerService.isPlaying() + " " + Variables.paused);
        if (!audioPlayerService.isPlaying() && !Variables.paused) {
            audioPlayerService.play(streamUrl);
            Variables.paused = false;
        } else if (audioPlayerService.isPlaying() && Variables.paused) {
            audioPlayerService.resume();
            Variables.paused = false;
        } else {
            audioPlayerService.pause();
            Variables.paused = true;
        }
        Animation animFadein = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        binding.playStopBtn.startAnimation(animFadein);
    }

    private String getAudioId(int id) {
        String surahId;
        if (id < 10)
            surahId = "00" + id;
        else if (id >= 100)
            surahId = Integer.toString(id);
        else
            surahId = "0" + id;
        return surahId;
    }


    //--------------------------------------------------------------------
    public void getData(String language, int postion) {

        surahDetailsViewModel = new ViewModelProvider(this).get(SurahDetailsViewModel.class);
        surahDetailsViewModel.getSurahDetails(language, postion + 1).observe(getActivity(),
                surahDetailsResponse -> {
                    list = surahDetailsResponse.getResult();
                    if (list.size() != 0) {
                        adapter = new SuraDetailsAdapter(list, getContext());
                        binding.recylerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void initRecycler() {
        list = new ArrayList<>();
        binding.recylerView.setHasFixedSize(true);
        binding.recylerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
                break;
            case R.id.playStopBtn:
                if (Variables.currentAudioId != Variables.position){
                    audioPlayerService.stop();
                    Variables.paused = false;
                }
                playStop(qreaa);
                break;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key == Variables.LANGUAGE) {
            String lang = prefs.getString(Variables.LANGUAGE, Variables.ENGLISH);
            getData(lang, Variables.position);
        } else if (key == Variables.READER) {

            qreaa = prefs.getString(Variables.READER, Variables.readerName);
            Variables.readerName = qreaa;
            audioPlayerService.stop();
            Variables.paused = false;
            playStop(qreaa);
        }
    }

    void initViews() {
        initRecycler();
        binding.setting.setOnClickListener(this);
        binding.playStopBtn.setOnClickListener(this);
    }


}