package com.andyanika.translator.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.andyanika.translator.repository.local.model.FavoriteModel;
import com.andyanika.translator.repository.local.model.WordFavoriteModel;
import com.andyanika.translator.repository.local.model.WordModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
interface TranslatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTranslation(WordModel word);

    @Query("SELECT words_table._id AS id," +
            "words_table.src AS textSrc, " +
            "words_table.dst AS textDst, " +
            "words_table.lang_src AS languageSrc, " +
            "words_table.lang_dst AS languageDst, " +
            "favorites_table._id AS favoriteId " +
            "from words_table " +
            "LEFT JOIN favorites_table ON words_table._id = favorites_table._id " +
            "ORDER BY words_table.last_modified DESC")
    Flowable<List<WordFavoriteModel>> getHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavorite(FavoriteModel favorite);

    @Delete
    void removeFavorite(FavoriteModel word);

    @Query("SELECT * from words_table " +
            "JOIN favorites_table ON words_table._id = favorites_table._id " +
            "ORDER BY favorites_table.last_modified DESC")
    Flowable<List<WordModel>> getFavorites();

    @Query("SELECT * from words_table " +
            "WHERE src = :text " +
            "AND lang_src = :langSrc " +
            "AND lang_dst = :langDst")
    Single<WordModel> getTranslation(String text, String langSrc, String langDst);
}