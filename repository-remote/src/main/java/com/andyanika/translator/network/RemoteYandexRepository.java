package com.andyanika.translator.network;

import com.andyanika.datasource.remote.BuildConfig;
import com.andyanika.translator.common.interfaces.RemoteRepository;
import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.TranslateResult;

import io.reactivex.Observable;

class RemoteYandexRepository implements RemoteRepository {
    private final NetworkYandexApi api;
    private final TranslationParamsBuilder directionBuilder;
    private ModelsAdapter modelsAdapter;
    private final String key;

    RemoteYandexRepository(NetworkYandexApi api, TranslationParamsBuilder directionBuilder, ModelsAdapter modelsAdapter) {
        this.api = api;
        this.directionBuilder = directionBuilder;
        this.modelsAdapter = modelsAdapter;
        this.key = BuildConfig.ApiKey;
        if (key.isEmpty()) {
            System.err.println("Yandex api key not defined in gradle.properties");
        }
    }

    @Override
    public Observable<TranslateResult> translate(TranslateRequest request) {
        String direction = directionBuilder.buildParam(request.direction);
        return api.translate(key, request.text, direction).map(response -> modelsAdapter.convert(request, response));
    }
}
