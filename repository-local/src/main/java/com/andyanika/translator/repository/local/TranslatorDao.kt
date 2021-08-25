package com.andyanika.translator.repository.local

import androidx.room.*
import com.andyanika.translator.repository.local.model.WordModel
import com.andyanika.translator.repository.local.model.WordFavoriteModel
import com.andyanika.translator.repository.local.model.FavoriteModel
import org.reactivestreams.Publisher

@Dao
interface TranslatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTranslation(word: WordModel?)

    @get:Query(
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
    val history: Publisher<List<WordFavoriteModel?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: FavoriteModel?)

    @Delete
    fun removeFavorite(word: FavoriteModel?)

    @get:Query(
        "SELECT * from words_table " +
            "JOIN favorites_table ON words_table._id = favorites_table._id " +
            "ORDER BY favorites_table.last_modified DESC"
    )
    val favorites: Publisher<List<WordModel?>?>?

    @Query(
        "SELECT * from words_table " +
            "WHERE src = :text " +
            "AND lang_src = :langSrc " +
            "AND lang_dst = :langDst"
    )
    fun getTranslation(text: String?, langSrc: String?, langDst: String?): WordModel?
}
