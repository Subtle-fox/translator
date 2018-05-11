package com.andyanika.translator.common;

import com.andyanika.translator.common.models.AvailableLanguagesResult;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import java.io.IOException;

public interface RemoteRepository {
    TranslateResult translate(TranslationRequest request) throws IOException;
    AvailableLanguagesResult getAvailableLanguages(LanguageCode languageCode) throws IOException;
}
