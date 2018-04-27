package com.andyanika.datasource.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "history_table")
public class HistoryModel {
    @NonNull
    @ColumnInfo(name = "word")
    public WordModel word;

    @NonNull
    @ColumnInfo(name = "last_used")
    public Long lastUsedMillis;
}
