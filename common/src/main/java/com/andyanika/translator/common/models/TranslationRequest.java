package com.andyanika.translator.common.models;

public class TranslationRequest {
    public String text;
    public TranslateDirection direction;

    public TranslationRequest(String text, TranslateDirection direction) {
        this.text = text;
        this.direction = direction;
    }
}