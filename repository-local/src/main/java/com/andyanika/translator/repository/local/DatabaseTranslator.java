package com.andyanika.translator.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.andyanika.translator.repository.local.model.FavoriteModel;
import com.andyanika.translator.repository.local.model.WordModel;

@Database(version = 1,
        entities = {
                WordModel.class,
                FavoriteModel.class})
abstract class DatabaseTranslator extends RoomDatabase {
    public abstract TranslatorDao translatorDao();
}
