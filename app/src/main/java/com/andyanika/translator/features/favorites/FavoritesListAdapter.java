package com.andyanika.translator.features.favorites;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.ui.OnClickListener;
import com.andyanika.usecases.RemoveFavoriteUseCase;

import java.util.ArrayList;
import java.util.List;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {
    private ArrayList<TranslationRowModel> data = new ArrayList<>();
    private RemoveFavoriteUseCase removeFavoriteUseCase;

    public FavoritesListAdapter(RemoveFavoriteUseCase removeFavoriteUseCase) {
        this.removeFavoriteUseCase = removeFavoriteUseCase;
    }

    private final OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(final int position) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    removeFavoriteUseCase.run(data.get(position).id);
                }
            }).start();
        }
    };

    public void setData(@Nullable List<TranslationRowModel> data) {
        this.data = data == null ? new ArrayList<TranslationRowModel>() : new ArrayList<>(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_row, parent, false);
        return new FavoritesViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        TranslationRowModel translateResult = data.get(position);
        holder.bind(translateResult);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
