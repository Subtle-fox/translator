package com.andyanika.translator.repository.remote;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;

import javax.inject.Inject;

class YandexTranslationParamsBuilder {
    @Inject
    YandexTranslationParamsBuilder() {
    }

    String buildParam(TranslateDirection<LanguageCode> direction) {
        return String.format("%s-%s", direction.src.toString(), direction.dst.toString()).toLowerCase();
    }
}