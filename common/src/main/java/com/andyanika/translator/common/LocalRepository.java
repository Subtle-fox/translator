package com.andyanika.translator.common;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface LocalRepository {
    Single<TranslateResult> translate(TranslationRequest request);
    void addTranslation(TranslateResult translateResult);
    Flowable<List<TranslationRowModel>> getHistory();

    Flowable<List<TranslationRowModel>> getFavorites();
    void addFavorites(int wordId);
    void removeFavorite(int wordId);

    TranslateDirection getLanguageDirection();
    void setLanguageDirection(TranslateDirection direction);
    List<LanguageCode> getAvailableLanguages();
}
