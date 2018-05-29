package com.andyanika.translator.features.select_lang;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.LanguageRowModel;
import com.andyanika.translator.di.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

@FragmentScope
public class SelectLanguageListAdapter extends RecyclerView.Adapter<SelectLanguageViewHolder> {
    private ArrayList<LanguageRowModel> data = new ArrayList<>();
    private PublishSubject<Integer> subject = PublishSubject.create();

    @Inject
    SelectLanguageListAdapter() {

    }

    Single<LanguageRowModel> getObservable() {
        return subject.map(position -> data.get(position)).singleOrError();
    }

    void setData(@Nullable List<LanguageRowModel> newData) {
        data = newData == null ? new ArrayList<>() : new ArrayList<>(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_row_select_language, parent, false);
        return new SelectLanguageViewHolder(itemView, subject);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectLanguageViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}