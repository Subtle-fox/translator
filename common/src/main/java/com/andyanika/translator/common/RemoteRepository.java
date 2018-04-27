package com.andyanika.translator.common;

import com.andyanika.translator.common.models.AvailableLanguagesResult;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

public interface RemoteRepository {
    TranslateResult translate(TranslationRequest request);
    AvailableLanguagesResult getAvailableLanguages();
}
