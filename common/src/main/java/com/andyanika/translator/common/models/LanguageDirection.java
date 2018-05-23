package com.andyanika.translator.common.models;

public class LanguageDirection {
    public LanguageCode src;
    public LanguageCode dst;

    public LanguageDirection(LanguageCode src, LanguageCode dst) {
        this.src = src;
        this.dst = dst;
    }
}
