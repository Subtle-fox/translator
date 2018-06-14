package com.andyanika.translator.common.models.ui;

import com.andyanika.translator.common.models.LanguageCode;

public class DisplayLanguageModel {
    public final LanguageCode code;
    public final String description;
    public final boolean isSelected;

    public DisplayLanguageModel(LanguageCode code, String description, boolean isSelected) {
        this.code = code;
        this.description = description;
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "DisplayLanguageModel{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}