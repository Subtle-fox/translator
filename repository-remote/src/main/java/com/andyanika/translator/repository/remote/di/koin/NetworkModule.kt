package com.andyanika.translator.repository.remote.di.koin

import com.andyanika.translator.common.interfaces.RemoteRepository
import com.andyanika.translator.repository.remote.*
import com.andyanika.translator.repository.remote.YandexApi
import com.andyanika.translator.repository.remote.YandexModelsAdapter
import com.andyanika.translator.repository.remote.YandexRemoteRepository
import com.andyanika.translator.repository.remote.YandexTranslationParamsBuilder
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val NETWORK_READ_TIMEOUT = 5
private const val NETWORK_WRITE_TIMEOUT = 5
private const val NETWORK_CONNECT_TIMEOUT = 15
private const val URL = "https://translate.yandex.net/api/v1.5/tr.json/"

val networkModule = module {
    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
            .readTimeout(NETWORK_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NETWORK_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(NETWORK_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        builder.build()
    }

    single {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    single<YandexRemoteRepository>(named("yandex")) bind RemoteRepository::class

    single<YandexTranslationParamsBuilder>()

    single<YandexModelsAdapter>()


    single<YandexApi> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .baseUrl(URL)
            .client(get())
            .build()
            .create(YandexApi::class.java)
    }

}
