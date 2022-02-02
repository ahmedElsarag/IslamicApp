package com.example.islamicapp.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.R;
import com.example.islamicapp.common.AzkarAdapterListener;
import com.example.islamicapp.databinding.AzkarItemBinding;
import com.example.islamicapp.databinding.SurahItemBinding;
import com.example.islamicapp.model.Azkar;

import java.util.List;

public class AzkarAdapter extends RecyclerView.Adapter<AzkarAdapter.AzkarHolder> {
    List<Azkar> list;
    Context context;
    AzkarAdapterListener AzkarAdapterListener;

    public AzkarAdapter(List<Azkar> list, Context context, AzkarAdapterListener AzkarAdapterListener) {
        this.list = list;
        this.context = context;
        this.AzkarAdapterListener = AzkarAdapterListener;
    }

    @NonNull
    @Override
    public AzkarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        AzkarItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.azkar_item, parent, false);
        return new AzkarHolder(binding, AzkarAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AzkarHolder holder, int position) {
        holder.binding.zekr.setText(list.get(position).getZekr());
        holder.binding.repeat.setText(Integer.toString(list.get(position).getRepeat()));
        String bless = list.get(position).getBless();
        if (!bless.isEmpty()){
            holder.binding.blees.setText(bless);
            holder.binding.blees.setVisibility(View.VISIBLE);
            holder.binding.divider.setVisibility(View.VISIBLE);
        }
        String start = list.get(position).getStart();
        if (!start.isEmpty()){
            holder.binding.start.setText(start);
            holder.binding.start.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 100, 0, 0);
            holder.binding.zekr.setLayoutParams(params);
        }

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

    public class AzkarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AzkarItemBinding binding;
        private AzkarAdapterListener AzkarAdapterListener;

        public AzkarHolder(@NonNull AzkarItemBinding binding, AzkarAdapterListener AzkarAdapterListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.AzkarAdapterListener = AzkarAdapterListener;
            binding.surahItem.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            AzkarAdapterListener.onAzkarClicked(getAdapterPosition(),binding.repeat);
        }
    }
}
