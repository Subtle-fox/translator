package com.andyanika.translator.common.models;

public class TranslateResult {
    public final String textSrc;
    public final String textTranslated;
    public final TranslateDirection direction;

    public TranslateResult(String textSrc, String textTranslated, TranslateDirection direction) {
        this.textSrc = textSrc;
        this.textTranslated = textTranslated;
        this.direction = direction;
    }
}
