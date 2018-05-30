package com.andyanika.translator.features.favorites;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;

public class DiffUtilsCallback extends DiffUtil.Callback {
    @NonNull
    private final List<TranslationRowModel> newData;
    @NonNull
    private final List<TranslationRowModel> oldData;

    DiffUtilsCallback(@NonNull List<TranslationRowModel> newData, @NonNull List<TranslationRowModel> oldData) {
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
