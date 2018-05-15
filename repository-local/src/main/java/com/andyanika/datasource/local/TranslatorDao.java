package com.andyanika.datasource.local;

import android.arch.persistence.room.*;
import android.support.annotation.Nullable;
import com.andyanika.datasource.local.model.FavoriteModel;
import com.andyanika.datasource.local.model.WordFavoriteModel;
import com.andyanika.datasource.local.model.WordModel;

import java.util.List;

@Dao
public interface TranslatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addTranslation(WordModel word);

    @Query("SELECT words_table._id AS id, words_table.src AS textSrc, words_table.dst AS textDst, favorites_table._id AS favoriteId" +
            " from words_table " +
            "LEFT JOIN favorites_table ON words_table._id = favorites_table._id " +
            "ORDER BY words_table.last_modified DESC")
    List<WordFavoriteModel> getHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavorite(FavoriteModel favorite);

    @Delete
    void removeFavorite(FavoriteModel word);

    @Query("SELECT * from words_table " +
            "JOIN favorites_table ON words_table._id = favorites_table._id " +
            "ORDER BY favorites_table.last_modified DESC")
    List<WordModel> getFavorites();

    @Nullable
    @Query("SELECT * from words_table " +
            "WHERE src = :text " +
            "AND lang_src = :langSrc " +
            "AND lang_dst = :langDst")
    WordModel getTranslation(String text, String langSrc, String langDst);
}