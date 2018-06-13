package com.andyanika.translator.feature.favorites;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.scopes.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

@FragmentScope
public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {
    private ArrayList<FavoriteModel> data;
    private Subject<Integer> subject;

    @Inject
    FavoritesListAdapter(Subject<Integer> subject) {
        this.data = new ArrayList<>();
        this.subject = subject;
    }

    public Observable<FavoriteModel> getObservable() {
        return subject.map(position -> data.get(position));
    }

    public void setData(@Nullable List<FavoriteModel> newData) {
        List<FavoriteModel> oldData = data;
        data = newData == null ? new ArrayList<>() : new ArrayList<>(newData);

        DiffUtilsCallback diffUtilsCallback = new DiffUtilsCallback(data, oldData);
        DiffUtil.calculateDiff(diffUtilsCallback).dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_row, parent, false);
        return new FavoritesViewHolder(itemView, subject);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        FavoriteModel translateResult = data.get(position);
        holder.bind(translateResult);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
