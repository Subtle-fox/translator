package com.andyanika.translator.common.models.ui;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.TranslateResult;

public class DisplayTranslateResult {
    public final String textSrc;
    public final String textTranslated;
    public final TranslateDirection<LanguageCode> direction;

    public boolean isError;
    public boolean isFound;
    public boolean isOffline;

    public DisplayTranslateResult(TranslateResult result, boolean isOffline) {
        this.textSrc = result.textSrc;
        this.textTranslated = result.textDst;
        this.direction = result.direction;
        this.isOffline = isOffline;
        this.isError = false;
        this.isFound = true;
    }

    public DisplayTranslateResult(String textSrc, String textTranslated, TranslateDirection<LanguageCode> direction, boolean isOffline) {
        this.textSrc = textSrc;
        this.textTranslated = textTranslated;
        this.direction = direction;
        this.isOffline = isOffline;
        this.isError = false;
        this.isFound = true;
    }

    public static DisplayTranslateResult createEmptyResult(TranslateRequest request, boolean isError) {
        DisplayTranslateResult noResult = new DisplayTranslateResult(request.getText(), null, request.getDirection(), false);
        noResult.isError = isError;
        noResult.isFound = false;
        return noResult;
    }
}
