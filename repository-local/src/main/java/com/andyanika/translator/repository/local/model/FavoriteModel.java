package com.andyanika.translator.repository.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
