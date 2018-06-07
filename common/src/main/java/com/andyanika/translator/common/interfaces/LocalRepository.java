package com.andyanika.translator.common.interfaces;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.common.models.UiTranslationModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalRepository {
    Single<TranslateResult> translate(TranslationRequest request);
    void addTranslation(TranslateResult translateResult);

    Flowable<List<UiTranslationModel>> getHistory();

    Flowable<List<UiTranslationModel>> getFavorites();
    void addFavorites(int wordId);
    void removeFavorite(int wordId);

    void setLanguageDirection(TranslateDirection direction);
    Observable<LanguageCode> getAvailableLanguagesObservable();
    Observable<LanguageCode> getSrcLanguage();
    Observable<LanguageCode> getDstLanguage();
}
