package com.andyanika.translator.repository.remote;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
class NetworkModule {
    private final static int NETWORK_READ_TIMEOUT = 5;
    private final static int NETWORK_WRITE_TIMEOUT = 5;
    private final static int NETWORK_CONNECT_TIMEOUT = 15;

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        return builder.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}