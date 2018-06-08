package com.andyanika.translator.network;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;

class TranslationParamsBuilder {
    String buildParam(TranslateDirection<LanguageCode> direction) {
        return String.format("%s-%s", direction.src.toString(), direction.dst.toString()).toLowerCase();
    }
}