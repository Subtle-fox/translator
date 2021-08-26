package com.andyanika.translator.repository.local

import androidx.room.*
import com.andyanika.translator.repository.local.entity.FavoriteEntity
import com.andyanika.translator.repository.local.entity.WordFavoriteEntity
import com.andyanika.translator.repository.local.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTranslation(word: WordEntity)

    @Query(
        "SELECT words_table._id AS id," +
            "words_table.src AS textSrc, " +
            "words_table.dst AS textDst, " +
            "words_table.lang_src AS languageSrc, " +
            "words_table.lang_dst AS languageDst, " +
            "favorites_table._id AS favoriteId " +
            "from words_table " +
            "LEFT JOIN favorites_table ON words_table._id = favorites_table._id " +
            "ORDER BY words_table.last_modified DESC"
    )
    fun getHistory(): Flow<List<WordFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun removeFavorite(word: FavoriteEntity)

    @Query(
        "SELECT * from words_table " +
            "JOIN favorites_table ON words_table._id = favorites_table._id " +
            "ORDER BY favorites_table.last_modified DESC"
    )
    fun getFavorites(): Flow<List<WordEntity>>

    @Query(
        "SELECT * from words_table " +
            "WHERE src = :text " +
            "AND lang_src = :langSrc " +
            "AND lang_dst = :langDst"
    )
    suspend fun getTranslation(text: String, langSrc: String, langDst: String): WordEntity?
}
