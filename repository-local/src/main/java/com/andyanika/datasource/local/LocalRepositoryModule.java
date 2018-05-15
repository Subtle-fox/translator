package com.andyanika.datasource.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.andyanika.translator.common.LocalRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalRepositoryModule {
    @Provides
    @Singleton
    public LocalRepository getDataSource(TranslatorDao dao, ModelsAdapter adapter) {
        return new LocalRepositoryImpl(dao, adapter);
    }

    @Provides
    @Singleton
    public TranslatorDao provideDatabase(Context context) {
        return Room.databaseBuilder(context, DatabaseTranslator.class, "words_database").build().translatorDao();
    }

    @Provides
    @Singleton
    public ModelsAdapter provideAdapter() {
        return new ModelsAdapter();
    }
}
