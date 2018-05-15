package com.andyanika.datasource.local.model;

import android.arch.persistence.room.Embedded;

import com.andyanika.translator.common.models.LanguageCode;

public class WordFavoriteModel {
    public int id;
    public String textSrc;
    public String textDst;
//    public LanguageCode languageSrc;
//    public LanguageCode languageDst;
    public int favoriteId;
}