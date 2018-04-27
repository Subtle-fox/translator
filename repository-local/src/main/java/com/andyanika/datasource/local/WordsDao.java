package com.andyanika.datasource.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.andyanika.datasource.local.model.WordModel;

import java.util.List;

@Dao
public interface WordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(WordModel word);

    @Query("SELECT * from word_table ORDER BY src ASC")
    List<WordModel> getAllWords();
}