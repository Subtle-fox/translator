package com.andyanika.translator.common.models;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TranslationRequest {
    public String text;
    public LanguageCode languageSrc;
    public LanguageCode languageDst;

    private TranslationRequest(String text, LanguageCode languageSrc, LanguageCode languageDst) {
        this.text = text;
        this.languageSrc = languageSrc;
        this.languageDst = languageDst;
    }

    public static TranslationRequest create(String text, LanguageCode languageSrc, LanguageCode languageDst) {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new TranslationRequest(encoded, languageSrc, languageDst);
    }
}