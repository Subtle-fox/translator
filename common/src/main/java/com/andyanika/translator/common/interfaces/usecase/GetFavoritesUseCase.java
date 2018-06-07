package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.UiTranslationModel;

import java.util.List;

import io.reactivex.Flowable;

public interface GetFavoritesUseCase {
    Flowable<List<UiTranslationModel>> run(int limit);
}
