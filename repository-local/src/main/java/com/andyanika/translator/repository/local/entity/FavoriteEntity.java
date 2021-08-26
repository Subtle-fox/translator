package com.andyanika.translator.repository.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites_table")
public class FavoriteEntity {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    public int id;

    @ColumnInfo(name = "last_modified")
    public long lastModified;

    public FavoriteEntity(int id) {
        this.id = id;
        this.lastModified = System.currentTimeMillis();
    }
}
