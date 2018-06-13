package com.andyanika.translator.repository.remote;

import com.andyanika.translator.common.interfaces.RemoteRepository;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = YandexRepositoryModule.Declarations.class)
class YandexRepositoryModule {
    @Provides
    @Singleton
    static String provideUrl() {
        return "https://translate.yandex.net/api/v1.5/tr.json/";
    }

    @Provides
    @Singleton
    static YandexApi provideApi(Retrofit retrofit) {
        return retrofit.create(YandexApi.class);
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(String baseUrl, Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Module
    interface Declarations {
        @Binds
        @Singleton
        @Named("yandex")
        RemoteRepository bindRemoteRepository(YandexRemoteRepository yandexRepository);
    }
}