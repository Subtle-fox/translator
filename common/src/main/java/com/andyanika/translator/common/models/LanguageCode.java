package com.andyanika.translator.common.models;

public enum LanguageCode {
    RU("ru"),
    UA("uk"),
    CH("zh"),
    EN("en");

    private final String value;

    LanguageCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}