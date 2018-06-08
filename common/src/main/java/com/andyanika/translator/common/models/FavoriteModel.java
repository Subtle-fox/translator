package com.andyanika.translator.common.models;

public class FavoriteModel {
    public TranslateResult translateResult;
    public boolean isFavorite;
    public int id;

    public FavoriteModel(int id, TranslateResult translateResult, boolean isFavorite) {
        this.translateResult = translateResult;
        this.isFavorite = isFavorite;
        this.id = id;
    }
}