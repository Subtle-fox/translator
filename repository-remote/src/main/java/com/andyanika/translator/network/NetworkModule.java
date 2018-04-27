package com.andyanika.translator.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Module
public class NetworkModule {
    private final static int NETWORK_READ_TIMEOUT = 10;
    private final static int NETWORK_WRITE_TIMEOUT = 10;
    private final static int NETWORK_CONNECT_TIMEOUT = 10;

    @Provides
    @Named("yandex")
    String provideUrl() {
        return "http://api.yandex.ru/translate/";
    }

    @Provides
    @Named("yandex")
    NetworkYandexApi provideApi(@Named("yandex") String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();

        return retrofit.create(NetworkYandexApi.class);
    }
}
