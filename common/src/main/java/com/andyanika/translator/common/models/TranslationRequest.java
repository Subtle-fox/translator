package com.andyanika.translator.common.models;

public class TranslationRequest {
    public String text;
    public String language;

    public TranslationRequest(String text, String language) {
        this.text = text;
        this.language = language;
    }
}
