package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.FavoriteModel;

import java.util.List;

import io.reactivex.Flowable;

public interface HistoryUseCase {
    Flowable<List<FavoriteModel>> run(String filter, int limit);
}
