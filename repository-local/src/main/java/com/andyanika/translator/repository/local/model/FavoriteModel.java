package com.andyanika.translator.repository.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorites_table")
public class FavoriteModel {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    public int id;

    @ColumnInfo(name = "last_modified")
    public long lastModified;

    public FavoriteModel(int id) {
        this.id = id;
        this.lastModified = System.currentTimeMillis();
    }
}
