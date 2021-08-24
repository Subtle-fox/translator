package com.andyanika.translator.repository.remote

import com.andyanika.translator.common.interfaces.RemoteRepository
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [YandexRepositoryModule.Declarations::class])
internal object YandexRepositoryModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideUrl(): String {
        return "https://translate.yandex.net/api/v1.5/tr.json/"
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): YandexApi {
        return retrofit.create<YandexApi>(YandexApi::class.java)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Module
    internal interface Declarations {
        @Binds
        @Singleton
        @Named("yandex")
        fun bindRemoteRepository(yandexRepository: YandexRemoteRepository?): RemoteRepository?
    }
}
