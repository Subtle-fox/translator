package com.andyanika.translator.network;

import com.andyanika.translator.common.models.LanguageCode;

public class TranslationParamsBuilder {
    public String buildDiractionParam(LanguageCode from, LanguageCode to) {
        return String.format("%s-%s", from.toString().toLowerCase(), to.toString().toLowerCase());
    }
}