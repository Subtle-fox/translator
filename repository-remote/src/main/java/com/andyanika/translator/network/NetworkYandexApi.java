package com.andyanika.translator.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkYandexApi {
	@GET("v2/me/locations/")
	Call<ResponseBody> translate(@Path("word") String word, @Path("language") String language);
}
