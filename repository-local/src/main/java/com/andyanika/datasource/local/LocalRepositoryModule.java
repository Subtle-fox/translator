package com.andyanika.datasource.local;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.andyanika.translator.common.interfaces.LocalRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class LocalRepositoryModule {
    @Provides
    @Singleton
    public LocalRepository getDataSource(TranslatorDao dao, SharedPreferences preferences, ModelsAdapter adapter, @Named("io") Scheduler ioScheduler) {
        return new LocalRepositoryImpl(dao, preferences, adapter, ioScheduler);
    }

    @Provides
    @Singleton
    public TranslatorDao provideDatabase(@Named("app") Context context) {
        return Room.databaseBuilder(context, DatabaseTranslator.class, "words_database").build().translatorDao();
    }

    @Provides
    @Singleton
    public ModelsAdapter provideAdapter() {
        return new ModelsAdapter();
    }

    @Provides
    @Singleton
    public SharedPreferences providePreferences(@Named("app") Context ctx) {
        return ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
    }
}
