package com.andyanika.translator.common.models;

import java.nio.channels.IllegalSelectorException;

public enum LanguageCode {
    RU,
    UK,
    ZH,
    EN;

    public static LanguageCode tryParse(String value, LanguageCode defaultValue) {
        if (value == null || value.isEmpty()) {
            System.err.print("LanguageCode: failed to parse: " + value);
            return defaultValue;
        }

        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalSelectorException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}