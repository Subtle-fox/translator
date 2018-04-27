package com.andyanika.datasource.local;

import com.andyanika.datasource.local.LocalRepositoryImpl;
import com.andyanika.translator.common.LocalRepository;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class LocalRepositoryModule {
    @Provides
    @Singleton
    public LocalRepository getDataSource() {
        return new LocalRepositoryImpl();
    }
}
