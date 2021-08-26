package com.andyanika.translator.repository.remote.yandex

import retrofit2.http.POST
import retrofit2.http.Query

internal interface YandexApi {
    @POST("translate")
    suspend fun translate(
        @Query("key") key: String?,
        @Query("text") text: String?,
        @Query("lang") direction: String?
    ): YandexTranslationResponse
}
