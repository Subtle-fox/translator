package com.andyanika.translator.repository.remote.di.koin

import core.interfaces.RemoteRepository
import com.andyanika.translator.repository.remote.ApiVariants
import com.andyanika.translator.repository.remote.BuildConfig
import com.andyanika.translator.repository.remote.stub.StubRemoteRepository
import com.andyanika.translator.repository.remote.yandex.YandexApi
import com.andyanika.translator.repository.remote.yandex.YandexModelsAdapter
import com.andyanika.translator.repository.remote.yandex.YandexRemoteRepository
import com.andyanika.translator.repository.remote.yandex.YandexTranslationParamsBuilder
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

    // region api variants

    single<YandexRemoteRepository>(named<ApiVariants.Yandex>()) bind RemoteRepository::class

    single(named<ApiVariants.Stub>()) { StubRemoteRepository } bind RemoteRepository::class

    // provided by default:
    single<RemoteRepository> { get(named<ApiVariants.Stub>()) }

    // endregion

}
