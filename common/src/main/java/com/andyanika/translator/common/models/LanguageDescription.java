package com.andyanika.translator.common.models;

public class LanguageDescription {
    public final LanguageCode code;
    public final String description;
    public final boolean isSrc;

    public LanguageDescription(LanguageCode code, String description, boolean isSrc) {
        this.code = code;
        this.description = description;
        this.isSrc = isSrc;
    }
}
