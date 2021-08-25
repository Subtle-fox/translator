package com.andyanika.translator.repository.remote
//
//import com.google.gson.FieldNamingPolicy
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import dagger.Module
//import dagger.Provides
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import java.util.concurrent.TimeUnit
//import javax.inject.Singleton
//
//@Module
//internal class NetworkModule {
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        val builder = OkHttpClient.Builder()
//            .readTimeout(NETWORK_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
//            .writeTimeout(NETWORK_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
//            .connectTimeout(NETWORK_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//        }
//        return builder.build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideGson(): Gson {
//        return GsonBuilder()
//            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//            .create()
//    }
//
//    companion object {
//        private const val NETWORK_READ_TIMEOUT = 5
//        private const val NETWORK_WRITE_TIMEOUT = 5
//        private const val NETWORK_CONNECT_TIMEOUT = 15
//    }
//}
