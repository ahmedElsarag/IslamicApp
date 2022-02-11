package com.example.islamicapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.example.islamicapp.R;
import com.example.islamicapp.Variables;
import com.example.islamicapp.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class BottomSheetFragment extends BottomSheetDialogFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FragmentBottomSheetBinding binding;
    private static final String TAG = "bottomsheetreader";

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(getLayoutInflater());
        sharedPreferences = getContext().getSharedPreferences(Variables.SHARED_PREFERANCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        binding.translationRG.setOnCheckedChangeListener(this);
        setDefaultLanguageRadio();
        setDefaultReader();
        initReader();

        return binding.getRoot();
    }

    private void initReader() {
        binding.include.abdelbaset.setOnClickListener(this);
        binding.include.mashary.setOnClickListener(this);
        binding.include.nasr.setOnClickListener(this);
        binding.include.alagmi.setOnClickListener(this);
        binding.include.fares.setOnClickListener(this);
        binding.include.yaser.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        View view = getView();
        view.post(() -> {
            View parent = (View) view.getParent();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());

        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch (i) {
            case R.id.english:
                editor.putString(Variables.LANGUAGE, Variables.ENGLISH);
                editor.apply();
                break;
            case R.id.persian:
                editor.putString(Variables.LANGUAGE, Variables.PERSIAN);
                editor.apply();
                break;
            case R.id.indonesian:
                editor.putString(Variables.LANGUAGE, Variables.INDONESIAN);
                editor.apply();
                break;
        }

    }

    private void setDefaultLanguageRadio() {
        String lang = sharedPreferences.getString(Variables.LANGUAGE, Variables.ENGLISH);
        switch (lang) {
            case Variables.ENGLISH:
                binding.english.setChecked(true);
                break;
            case Variables.PERSIAN:
                binding.persian.setChecked(true);
                break;
            case Variables.INDONESIAN:
                binding.indonesian.setChecked(true);
                break;
        }


    }

    private void setDefaultReader() {
        String reader = sharedPreferences.getString(Variables.READER, Variables.readerName);
        switch (reader) {
            case "abdul_basit_murattal":
                binding.include.abdelbaset.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                break;
            case "mishaari_raashid_al_3afaasee":
                binding.include.mashary.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                break;
            case "yasser_ad-dussary":
                binding.include.yaser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                break;
            case "ahmed_ibn_3ali_al-3ajamy":
                binding.include.alagmi.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                break;
            case "fares":
                binding.include.fares.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                break;
            case "nasser_bin_ali_alqatami":
                binding.include.nasr.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                break;
        }


    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: "+view.getId());
        switch (view.getId()) {
            case R.id.abdelbaset:
                Log.d(TAG, "onClick: "+R.id.abdelbaset);
                binding.include.abdelbaset.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                //change the largeicon of notification player in description adapter
                Variables.readerImage = R.drawable.abdelbaset;
                selectReader("abdul_basit_murattal");
                break;
            case R.id.mashary:
                Log.d(TAG, "onClick: "+R.id.mashary);
                binding.include.mashary.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                Variables.readerImage = R.drawable.al3fasi;
                selectReader("mishaari_raashid_al_3afaasee");
                break;
            case R.id.yaser:
                binding.include.yaser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                Variables.readerImage = R.drawable.eldosry;
                selectReader("yasser_ad-dussary");
                break;
            case R.id.alagmi:
                binding.include.alagmi.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                Variables.readerImage = R.drawable.alajmi;
                selectReader("ahmed_ibn_3ali_al-3ajamy");
                break;
            case R.id.fares:
                binding.include.fares.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                Variables.readerImage = R.drawable.fares;
                selectReader("fares");
                break;
            case R.id.nasr:
                binding.include.nasr.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.reader_selected_item));
                Variables.readerImage = R.drawable.naser;
                selectReader("nasser_bin_ali_alqatami");
                break;

        }
    }

    private void selectReader(String reader) {
        editor.putString(Variables.READER, reader);
        editor.apply();
    }
}