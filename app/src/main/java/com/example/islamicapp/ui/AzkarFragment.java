package com.example.islamicapp.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islamicapp.R;
import com.example.islamicapp.adapter.AzkarAdapter;
import com.example.islamicapp.common.AzkarAdapterListener;
import com.example.islamicapp.common.LocalJsonData;
import com.example.islamicapp.Variables;
import com.example.islamicapp.databinding.FragmentAzkarBinding;
import com.example.islamicapp.model.Azkar;

import java.util.ArrayList;
import java.util.List;

public class AzkarFragment extends Fragment implements AzkarAdapterListener, View.OnClickListener {
    private static final String TAG = "azkardisplay";
    List<Azkar> morningAzkar;
    FragmentAzkarBinding binding;
    AzkarAdapter adapter;
    int count = 1;

    public AzkarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAzkarBinding.inflate(getLayoutInflater());
        initRecycler();
        binding.morningCard.setOnClickListener(this);
        binding.eveningCard.setOnClickListener(this);

        morningAzkar = LocalJsonData.getDataFromJson(Variables.MORNING_JSON,getContext());
        setAdapter();

        return binding.getRoot();
    }

    private void initRecycler() {
        morningAzkar = new ArrayList<>();
        binding.recylerView.setHasFixedSize(true);
        binding.recylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //without this when scroll and return value return to default
        binding.recylerView.setItemViewCacheSize(45);
    }

    public void myOnKeyDown(int key_code, Context context) {
        //do whatever you want here
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
    }

    @Override
    public void onAzkarClicked(int postion, View view) {
        Log.d(TAG, "onSurahListener: " + postion);
        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        count = Integer.parseInt((String) ((TextView) view).getText());
        if (count != 0) {
            if (count == 1) {
                count = Integer.parseInt((String) ((TextView) view).getText()) - 1;
                ((TextView) view).setText(String.valueOf(count));
                vibe.vibrate(400);
            } else {
                count = Integer.parseInt((String) ((TextView) view).getText()) - 1;
                ((TextView) view).setText(String.valueOf(count));
                vibe.vibrate(100);
            }

        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.morning_card:
                binding.morningCard.setCardBackgroundColor(getResources().getColor(R.color.purple_500));
                binding.tvMorning.setTextColor(getResources().getColor(R.color.white));
                binding.eveningCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                binding.tvEvening.setTextColor(getResources().getColor(R.color.black));
                morningAzkar = LocalJsonData.getDataFromJson(Variables.MORNING_JSON,getContext());
                setAdapter();
                break;
            case R.id.evening_card:
                binding.eveningCard.setCardBackgroundColor(getResources().getColor(R.color.purple_500));
                binding.tvEvening.setTextColor(getResources().getColor(R.color.white));
                binding.morningCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                binding.tvMorning.setTextColor(getResources().getColor(R.color.black));
                morningAzkar = LocalJsonData.getDataFromJson(Variables.EVENING_JSON,getContext());
                setAdapter();
                break;
        }
    }

    private void setAdapter(){
        adapter = new AzkarAdapter(morningAzkar,getContext(),this);
        binding.recylerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}