package com.andyanika.translator.common.models;

public class TranslateResult {
    public String textSrc;
    public String textTranslated;
    private final LanguageCode languageSrc;
    private final LanguageCode languageDst;

    public TranslateResult(String textSrc, String textTranslated, LanguageCode languageSrc, LanguageCode languageDst) {
        this.textSrc = textSrc;
        this.textTranslated = textTranslated;
        this.languageSrc = languageSrc;
        this.languageDst = languageDst;
    }
}
