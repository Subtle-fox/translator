package com.andyanika.translator.common.models;

public class UiLanguageModel {
    public final LanguageCode code;
    public final String description;
    public final boolean isCurrent;

    public UiLanguageModel(LanguageCode code, String description, boolean isCurrent) {
        this.code = code;
        this.description = description;
        this.isCurrent = isCurrent;
    }
}
