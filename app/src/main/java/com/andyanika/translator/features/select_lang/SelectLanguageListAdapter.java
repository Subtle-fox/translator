package com.andyanika.translator.features.select_lang;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyanika.translator.R;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.features.favorites.DiffUtilsCallback;
import com.andyanika.translator.features.favorites.FavoritesViewHolder;
import com.andyanika.translator.ui.OnClickListener;
import com.andyanika.usecases.RemoveFavoriteUseCase;

import java.util.ArrayList;
import java.util.List;

public class SelectLanguageListAdapter extends RecyclerView.Adapter<SelectLanguageViewHolder> {
    private ArrayList<LanguageCode> data = new ArrayList<>();
    private RemoveFavoriteUseCase removeFavoriteUseCase;

    public SelectLanguageListAdapter(RemoveFavoriteUseCase removeFavoriteUseCase) {
        this.removeFavoriteUseCase = removeFavoriteUseCase;
    }

    public void setData(@Nullable List<LanguageCode> newData) {
        data = newData == null ? new ArrayList<>() : new ArrayList<>(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_row_select_language, parent, false);
        return new SelectLanguageViewHolder(itemView);
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
