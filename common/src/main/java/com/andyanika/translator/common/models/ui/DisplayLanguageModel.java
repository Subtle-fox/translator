package com.andyanika.translator.common.models.ui;

import com.andyanika.translator.common.models.LanguageCode;

public class DisplayLanguageModel {
    public final LanguageCode code;
    public final String description;
    public final boolean isCurrent;

    public DisplayLanguageModel(LanguageCode code, String description, boolean isCurrent) {
        this.code = code;
        this.description = description;
        this.isCurrent = isCurrent;
    }
}