package com.andyanika.datasource.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.andyanika.datasource.local.model.FavoriteModel;
import com.andyanika.datasource.local.model.WordModel;

@Database(version = 1,
        entities = {
                WordModel.class,
                FavoriteModel.class})
abstract class DatabaseTranslator extends RoomDatabase {
    public abstract TranslatorDao translatorDao();
}
