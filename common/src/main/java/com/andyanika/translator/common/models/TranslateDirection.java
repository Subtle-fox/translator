package com.andyanika.translator.common.models;

public class TranslateDirection {
    public LanguageCode src;
    public LanguageCode dst;

    public TranslateDirection(LanguageCode src, LanguageCode dst) {
        this.src = src;
        this.dst = dst;
    }
}
