package com.andyanika.translator.repository.remote;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface YandexApi {
    @POST("translate")
    Observable<YandexTranslationResponse> translate(@Query("key") String key,
                                                    @Query("text") String text,
                                                    @Query("lang") String direction);
}
