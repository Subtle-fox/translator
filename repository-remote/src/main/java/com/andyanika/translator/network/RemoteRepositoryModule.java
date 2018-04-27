package com.andyanika.translator.network;

import com.andyanika.translator.common.RemoteRepository;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module(includes = NetworkModule.class)
public class RemoteRepositoryModule {
    @Singleton
    @Provides
    public RemoteRepository provideYandexRepository(@Named("yandex") NetworkYandexApi api) {
        return new RemoteYandexRepository(api);
    }
}