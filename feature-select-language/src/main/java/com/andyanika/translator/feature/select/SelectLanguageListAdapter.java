package com.andyanika.translator.feature.select;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.translator.common.models.ui.DisplayLanguageModel;
import com.andyanika.translator.common.scopes.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.Subject;

@FragmentScope
public class SelectLanguageListAdapter extends RecyclerView.Adapter<SelectLanguageViewHolder> {
    private ArrayList<DisplayLanguageModel> data = new ArrayList<>();
    private Subject<Integer> subject;

    @Inject
    SelectLanguageListAdapter(Subject<Integer> subject) {
        this.subject = subject;
    }

    Single<DisplayLanguageModel> getObservable() {
        return subject.map(position -> data.get(position)).singleOrError();
    }

    void setData(@Nullable List<DisplayLanguageModel> newData) {
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
