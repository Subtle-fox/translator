package com.andyanika.translator.network;

import com.andyanika.translator.common.models.TranslateDirection;

public class TranslationParamsBuilder {
    public String buildParam(TranslateDirection direction) {
        return String.format("%s-%s", direction.src.toString(), direction.dst.toString()).toLowerCase();
    }
}