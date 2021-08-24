package com.andyanika.translator.repository.remote

import com.andyanika.translator.common.models.TranslateResult
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
