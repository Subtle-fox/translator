package com.andyanika.translator.common.models;

public class TranslateResult {
    public final String textSrc;
    public final String textTranslated;
    public final TranslateDirection direction;

    public boolean isOffline;
    public boolean isSuccess;

    public TranslateResult(String textSrc, String textTranslated, TranslateDirection direction) {
        this.textSrc = textSrc;
        this.textTranslated = textTranslated;
        this.direction = direction;
        this.isSuccess = true;
        this.isOffline = true;
    }

    public static TranslateResult createErrorResult(String textSrc, TranslateDirection direction, boolean isOffline) {
        TranslateResult noResult = new TranslateResult(textSrc, "< Перевод не найден >", direction);
        noResult.isOffline = isOffline;
        noResult.isSuccess = false;
        return noResult;
    }
}
