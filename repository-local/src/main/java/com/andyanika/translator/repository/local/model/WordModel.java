package com.andyanika.translator.repository.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "words_table",
        indices = {@Index(value = {"src", "lang_src", "lang_dst"}, unique = true)})
public class WordModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public int id;

    @NonNull
    @ColumnInfo(name = "lang_src")
    public String languageSrc;

    @NonNull
    @ColumnInfo(name = "lang_dst")
    public String languageDst;

    @NonNull
    @ColumnInfo(name = "src")
    public String textSrc;

    @NonNull
    @ColumnInfo(name = "dst")
    public String textDst;

    @ColumnInfo(name = "last_modified")
    public long lastModified;

    public WordModel(@NonNull String textSrc, @NonNull String textDst, @NonNull String languageSrc, @NonNull String languageDst) {
        this.textSrc = textSrc;
        this.textDst = textDst;
        this.languageSrc = languageSrc;
        this.languageDst = languageDst;
        this.lastModified = System.currentTimeMillis();
    }
}
