package com.andyanika.translator.common;

import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;

public interface LocalRepository {
    TranslateResult translate(TranslationRequest request);
    long addTranslation(TranslateResult translateResult);
    List<TranslationRowModel> getHistory();

    List<TranslationRowModel> getFavorites();
    void addFavorites(int wordId);
    void removeFavorite(int wordId);
}
