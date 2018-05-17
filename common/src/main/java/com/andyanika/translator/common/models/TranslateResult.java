package com.andyanika.translator.common.models;

public class TranslateResult {
    public final String textSrc;
    public final String textTranslated;
    public final LanguageCode languageSrc;
    public final LanguageCode languageDst;

    public TranslateResult(String textSrc, String textTranslated, LanguageCode languageSrc, LanguageCode languageDst) {
        this.textSrc = textSrc;
        this.textTranslated = textTranslated;
        this.languageSrc = languageSrc;
        this.languageDst = languageDst;
    }
}
