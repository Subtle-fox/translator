package com.andyanika.translator.common;

import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface LocalRepository {
    Maybe<TranslateResult> translate(TranslationRequest request);
    long addTranslation(TranslateResult translateResult);
    Flowable<List<TranslationRowModel>> getHistory();

    Flowable<List<TranslationRowModel>> getFavorites();
    void addFavorites(int wordId);
    void removeFavorite(int wordId);
}
