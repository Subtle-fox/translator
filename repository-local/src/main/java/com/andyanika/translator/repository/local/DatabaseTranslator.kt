package com.andyanika.translator.repository.local

import androidx.room.Database
import com.andyanika.translator.repository.local.entity.WordEntity
import androidx.room.RoomDatabase
import com.andyanika.translator.repository.local.entity.FavoriteEntity

@Database(version = 1, entities = [WordEntity::class, FavoriteEntity::class])
internal abstract class DatabaseTranslator : RoomDatabase() {
    abstract fun translatorDao(): TranslatorDao
}
