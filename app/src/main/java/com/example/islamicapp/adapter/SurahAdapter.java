package com.example.islamicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.R;
import com.example.islamicapp.common.AdapterListener;
import com.example.islamicapp.databinding.SurahItemBinding;
import com.example.islamicapp.model.Surah;

import java.util.List;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahHolder> {
    List<Surah> list;
    Context context;
    AdapterListener surahAdapterListener;

    public SurahAdapter(List<Surah> list, Context context, AdapterListener surahAdapterListener) {
        this.list = list;
        this.context = context;
        this.surahAdapterListener = surahAdapterListener;
    }

    @NonNull
    @Override
    public SurahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SurahItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.surah_item, parent, false);
        return new SurahHolder(binding, surahAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahHolder holder, int position) {
        holder.binding.surahEnglishName.setText(list.get(position).getEnglishName());
        holder.binding.surahArabicName.setText(list.get(position).getName());
        holder.binding.ayatNum.setText(Integer.toString(list.get(position).getNumberOfAyahs()));
        holder.binding.surahNumber.setText(Integer.toString(list.get(position).getNumber()));
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

    public class SurahHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SurahItemBinding binding;
        private AdapterListener surahAdapterListener;

        public SurahHolder(@NonNull SurahItemBinding binding, AdapterListener surahAdapterListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.surahAdapterListener = surahAdapterListener;
            binding.surahItem.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            surahAdapterListener.onSurahListener(getAdapterPosition(),list.get(getAdapterPosition()).getName());
        }
    }
}
