package com.andyanika.translator.feature.history;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.translator.common.models.UiTranslationModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

@FragmentScope
public class HistoryListAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private ArrayList<UiTranslationModel> data = new ArrayList<>();
    private Subject<Integer> subject;

    @Inject
    HistoryListAdapter(Subject<Integer> subject) {
        this.subject = subject;
    }

    Observable<UiTranslationModel> getObservable() {
        return subject.map(position -> data.get(position));
    }

    void setData(@Nullable List<UiTranslationModel> data) {
        this.data = data == null ? new ArrayList<>() : new ArrayList<>(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_row, parent, false);
        return new HistoryViewHolder(itemView, subject);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        UiTranslationModel translateResult = data.get(position);
        holder.bind(translateResult);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}