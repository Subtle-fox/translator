package com.andyanika.translator.feature.favorites;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.andyanika.translator.common.models.FavoriteModel;

import java.util.List;

public class DiffUtilsCallback extends DiffUtil.Callback {
    @NonNull
    private final List<FavoriteModel> newData;
    @NonNull
    private final List<FavoriteModel> oldData;

    DiffUtilsCallback(@NonNull List<FavoriteModel> newData, @NonNull List<FavoriteModel> oldData) {
        this.newData = newData;
        this.oldData = oldData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).id == newData.get(newItemPosition).id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldItemPosition, newItemPosition);
    }
}
