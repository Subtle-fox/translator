package com.andyanika.datasource.local;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.andyanika.translator.common.LocalRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalRepositoryModule {
    @Provides
    @Singleton
    public LocalRepository getDataSource(TranslatorDao dao, SharedPreferences preferences, ModelsAdapter adapter) {
        return new LocalRepositoryImpl(dao, preferences, adapter);
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

    @Provides
    @Singleton
    public SharedPreferences providePreferences(Context ctx) {
        return ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
    }
}
