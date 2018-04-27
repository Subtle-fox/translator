package com.andyanika.datasource.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class WordModel {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "lang_src")
    public String langSrc;

    @NonNull
    @ColumnInfo(name = "lang_dst")
    public String langDst;

    @NonNull
    @ColumnInfo(name = "src")
    public String src;

    @NonNull
    @ColumnInfo(name = "dst")
    public String dst;
}
