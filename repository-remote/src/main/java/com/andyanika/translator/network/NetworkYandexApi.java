package com.andyanika.translator.network;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkYandexApi {
    @POST("translate")
    Observable<TranslationResponse> translate(@Query("key") String key,
                                              @Query("text") String text,
                                              @Query("lang") String direction);
}
