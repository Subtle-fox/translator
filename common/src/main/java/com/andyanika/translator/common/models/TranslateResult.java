package com.andyanika.translator.common.models;

public class TranslateResult {
    public final String textSrc;
    public final String textTranslated;
    public final TranslateDirection direction;

    public boolean isError;
    public boolean isFound;
    public boolean isOffline;

    public TranslateResult(String textSrc, String textTranslated, TranslateDirection direction, boolean isOffline) {
        this.textSrc = textSrc;
        this.textTranslated = textTranslated;
        this.direction = direction;
        this.isOffline = isOffline;
        this.isError = false;
        this.isFound = true;
    }

    public static TranslateResult createEmptyResult(String textSrc, TranslateDirection direction, boolean isError) {
        TranslateResult noResult = new TranslateResult(textSrc, "< Перевод не найден >", direction, false);
        noResult.isError = isError;
        noResult.isFound = false;
        return noResult;
    }
}
