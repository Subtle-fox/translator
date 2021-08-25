package com.andyanika.translator.repository.local

import androidx.room.Database
import com.andyanika.translator.repository.local.model.WordModel
import androidx.room.RoomDatabase
import com.andyanika.translator.repository.local.TranslatorDao
import com.andyanika.translator.repository.local.model.FavoriteModel

@Database(version = 1, entities = [WordModel::class, FavoriteModel::class])
internal abstract class DatabaseTranslator : RoomDatabase() {
    abstract fun translatorDao(): TranslatorDao
}
