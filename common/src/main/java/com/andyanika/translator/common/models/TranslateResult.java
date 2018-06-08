package com.andyanika.translator.common.models;

public class TranslateResult {
    public final String textSrc;
    public final String textDst;
    public final TranslateDirection<LanguageCode> direction;

    public TranslateResult(String textSrc, String textDst, TranslateDirection<LanguageCode> direction) {
        this.textSrc = textSrc;
        this.textDst = textDst;
        this.direction = direction;
    }
}