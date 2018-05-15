package com.andyanika.translator.common.models;

public class TranslationRequest {
    public String text;
    public LanguageCode languageSrc;
    public LanguageCode languageDst;

    public TranslationRequest(String text, LanguageCode languageSrc, LanguageCode languageDst) {
        this.text = text;
        this.languageSrc = languageSrc;
        this.languageDst = languageDst;
    }
}