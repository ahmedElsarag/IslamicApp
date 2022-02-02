package com.example.islamicapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.islamicapp.R;
import com.example.islamicapp.common.SurahMediaPlayer;
import com.example.islamicapp.adapter.SuraDetailsAdapter;
import com.example.islamicapp.Variables;
import com.example.islamicapp.databinding.FragmentSurahDetailsBinding;
import com.example.islamicapp.model.SurahDetails;
import com.example.islamicapp.viewmodel.SurahDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailsFragment extends Fragment implements View.OnClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {
    FragmentSurahDetailsBinding binding;
    SurahDetailsViewModel surahDetailsViewModel;
    List<SurahDetails> list;
    SuraDetailsAdapter adapter;
    SharedPreferences prefs;
    SurahMediaPlayer surahMediaPlayer;

    private static final String TAG = "fragmsurah";

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
        getData(translationLanguage, Variables.position);
        prefs.registerOnSharedPreferenceChangeListener(this);
        binding.surahName.setText(Variables.surahName);

        return binding.getRoot();
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChange() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //when drag the seekbar
                    surahMediaPlayer.onSeekbarDraged(progress);
                }
                //set current time
                binding.currentTime.setText(surahMediaPlayer.getCurrentPosition());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
    }

    private MediaPlayer.OnCompletionListener complete() {
        return new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                binding.play.setVisibility(View.VISIBLE);
                binding.pause.setVisibility(View.GONE);
                binding.seekBar.setProgress(0);
                surahMediaPlayer.mediaPlayer.seekTo(0);
                surahMediaPlayer.onComplete();
            }
        };
    }

    private void getMediaPlayer(String qarea, int id) {
        String surahId;
        if (id < 10)
            surahId = "00" + id;
        else if (id >= 100)
            surahId = Integer.toString(id);
        else
            surahId = "0" + id;
        surahMediaPlayer.initMediaPlayer(binding.seekBar, qarea, surahId);
        binding.duration.setText(surahMediaPlayer.getDuration());
    }

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
            case R.id.play:
                String qreaa = prefs.getString(Variables.READER, Variables.readerName);
                getMediaPlayer(qreaa, Variables.position + 1);
                surahMediaPlayer.mediaPlayer.setOnCompletionListener(complete());
                binding.play.setVisibility(View.GONE);
                binding.pause.setVisibility(View.VISIBLE);
                surahMediaPlayer.playMediaPlayer(binding.seekBar);
                break;
            case R.id.pause:
                binding.play.setVisibility(View.VISIBLE);
                binding.pause.setVisibility(View.GONE);
                surahMediaPlayer.pauseMediaPlayer();

        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key == Variables.LANGUAGE) {
            String lang = prefs.getString(Variables.LANGUAGE, Variables.ENGLISH);
            getData(lang, Variables.position);
        } else if (key == Variables.READER) {
            if (surahMediaPlayer.mediaPlayer != null) {
                String reader = prefs.getString(Variables.READER, Variables.readerName);
                surahMediaPlayer.readerChange();
                surahMediaPlayer = SurahMediaPlayer.getInstance(getContext());
                binding.play.setVisibility(View.VISIBLE);
                binding.pause.setVisibility(View.GONE);
                getMediaPlayer(reader, Variables.position + 1);
                binding.seekBar.setProgress(0);
            }
        }
    }

    void initViews() {
        initRecycler();
        surahMediaPlayer = SurahMediaPlayer.getInstance(getContext());
        binding.setting.setOnClickListener(this);
        binding.seekBar.setOnSeekBarChangeListener(onSeekBarChange());
        binding.pause.setOnClickListener(this);
        binding.play.setOnClickListener(this);
    }

}