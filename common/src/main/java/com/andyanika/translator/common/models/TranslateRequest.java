package com.andyanika.translator.common.models;

public class TranslateRequest {
    public String text;
    public TranslateDirection<LanguageCode> direction;

    public TranslateRequest(String text, TranslateDirection<LanguageCode> direction) {
        this.text = text;
        this.direction = direction;
    }
}