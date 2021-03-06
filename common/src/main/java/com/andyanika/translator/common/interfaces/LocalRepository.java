package com.andyanika.translator.common.interfaces;

import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.TranslateResult;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface LocalRepository {
    Single<TranslateResult> translate(TranslateRequest request);
    Single<TranslateResult> addTranslation(TranslateResult translateResult);

    Flowable<List<FavoriteModel>> getHistory();

    Flowable<List<FavoriteModel>> getFavorites();
    void addFavorites(int wordId);
    void removeFavorite(int wordId);

    void setLanguageDirection(TranslateDirection<LanguageCode> direction);
    Observable<LanguageCode> getAvailableLanguages();
    Observable<LanguageCode> getSrcLanguage();
    Observable<LanguageCode> getDstLanguage();
}
