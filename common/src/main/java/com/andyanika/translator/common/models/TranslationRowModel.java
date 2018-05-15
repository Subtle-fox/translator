package com.andyanika.translator.common.models;

public class TranslationRowModel {
    public TranslateResult translateResult;
    public boolean isFavorite;
    public int id;

    public TranslationRowModel(int id, TranslateResult translateResult, boolean isFavorite) {
        this.translateResult = translateResult;
        this.isFavorite = isFavorite;
        this.id = id;
    }
}
