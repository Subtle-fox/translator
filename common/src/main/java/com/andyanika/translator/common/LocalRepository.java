package com.andyanika.translator.common;

import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import java.util.List;

public interface LocalRepository {
    List<TranslateResult> getHistory();
    List<TranslateResult> getFavorites();

    TranslateResult translate(TranslationRequest request);
}
