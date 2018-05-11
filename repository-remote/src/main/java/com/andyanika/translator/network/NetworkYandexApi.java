package com.andyanika.translator.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkYandexApi {
    @POST("getLangs")
    Call<ResponseBody> getAvailableLanguages(@Query("key") String key,
                                             @Query("ui") String languageCode);

    @POST("translate")
    Call<TranslationResponse> translate(@Query("key") String key,
                                 @Query("text") String text,
                                 @Query("lang") String direction);
}
