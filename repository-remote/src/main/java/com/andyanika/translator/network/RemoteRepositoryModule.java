package com.andyanika.translator.network;

import com.andyanika.translator.common.interfaces.RemoteRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = NetworkModule.class)
public class RemoteRepositoryModule {
    @Singleton
    @Provides
    RemoteRepository provideYandexRepository(@Named("yandex") NetworkYandexApi api, TranslationParamsBuilder paramsBuilder, ModelsAdapter adapter) {
        return new RemoteYandexRepository(api, paramsBuilder, adapter);
    }

    @Singleton
    @Provides
    TranslationParamsBuilder provideTranslationParamsBuilder() {
        return new TranslationParamsBuilder();
    }

    @Singleton
    @Provides
    ModelsAdapter provideModelAdapter() {
        return new ModelsAdapter();
    }
}