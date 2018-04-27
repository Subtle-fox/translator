package com.andyanika.translator.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.andyanika.translator.R;
import com.andyanika.translator.common.models.TranslateResult;

import java.util.ArrayList;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private ArrayList<TranslateResult> data = new ArrayList<>();

    public void setData(@Nullable List<TranslateResult> data) {
        this.data = data == null ? new ArrayList<TranslateResult>() : new ArrayList<>(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_row, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        TranslateResult translateResult = data.get(position);
        holder.bind(translateResult);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(TranslateResult result) {
        data.add(0, result);
    }
}
