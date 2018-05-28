package com.andyanika.translator.common.models;

public class LanguageDescription {
    public final LanguageCode code;
    public final String description;

    public LanguageDescription(LanguageCode code, String description) {
        this.code = code;
        this.description = description;
    }
}
