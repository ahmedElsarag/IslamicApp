package com.example.islamicapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamicapp.common.NavigationHost;
import com.example.islamicapp.common.AdapterListener;
import com.example.islamicapp.Variables;
import com.example.islamicapp.adapter.SurahAdapter;
import com.example.islamicapp.databinding.FragmentSurahBinding;
import com.example.islamicapp.model.Surah;
import com.example.islamicapp.viewmodel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;

public class SurahFragment extends Fragment implements AdapterListener {
    SurahViewModel surahViewModel;
    private SurahAdapter surahAdapter;
    FragmentSurahBinding binding;
    public static List<Surah> list;
    public SurahFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSurahBinding.inflate(getLayoutInflater());
        initRecycler();

        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);
        surahViewModel.getSurah().observe(getActivity(), surahResp -> {
            list = surahResp.getData();
            if (list.size() != 0){
                surahAdapter = new SurahAdapter(list, getContext(), this);
                binding.surahLisView.setAdapter(surahAdapter);
                surahAdapter.notifyDataSetChanged();
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    private void initRecycler(){
        list = new ArrayList<>();
        binding.surahLisView.setHasFixedSize(true);
        binding.surahLisView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onSurahListener(int position, String name) {
       ((NavigationHost) (getActivity())).navigateTo(new SurahDetailsFragment(), true);
        Variables.position = position;
        Variables.surahName = name;

    }


}