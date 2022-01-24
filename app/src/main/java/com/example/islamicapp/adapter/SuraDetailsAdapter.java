package com.example.islamicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.R;
import com.example.islamicapp.databinding.SurahDetailsItemBinding;
import com.example.islamicapp.model.Surah;
import com.example.islamicapp.model.SurahDetails;

import java.util.List;

public class SuraDetailsAdapter extends RecyclerView.Adapter<SuraDetailsAdapter.SurahHolder> {
    List<SurahDetails> list;
    Context context;

    public SuraDetailsAdapter(List<SurahDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SurahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SurahDetailsItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.surah_details_item, parent, false);
        return new SurahHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahHolder holder, int position) {
       holder.binding.ayahArabicContent.setText(list.get(position).arabic_text);
       holder.binding.ayahEnglishContent.setText(list.get(position).translation);
       holder.binding.ayahNum.setText(Integer.toString(list.get(position).aya));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SurahHolder extends RecyclerView.ViewHolder {
        SurahDetailsItemBinding binding;

        public SurahHolder(@NonNull SurahDetailsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
