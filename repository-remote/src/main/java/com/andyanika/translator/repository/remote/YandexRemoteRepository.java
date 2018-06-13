package com.andyanika.translator.repository.remote;

import com.andyanika.translator.common.interfaces.RemoteRepository;
import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.TranslateResult;

import javax.inject.Inject;

import io.reactivex.Observable;

class YandexRemoteRepository implements RemoteRepository {
    private final YandexApi api;
    private final YandexTranslationParamsBuilder directionBuilder;
    private final YandexModelsAdapter modelsAdapter;
    private final String key;

    @Inject
    YandexRemoteRepository(YandexApi api, YandexTranslationParamsBuilder directionBuilder, YandexModelsAdapter modelsAdapter) {
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
