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
        String direction = directionBuilder.buildParam(request.getDirection());
        return api.translate(key, request.getText(), direction)
                .map(response -> modelsAdapter.convert(request, response))
                .filter(modelsAdapter::isTranslationFound);
    }

//    private DisplayTranslateResult getDisplayTranslateResult(TranslateRequest request, TranslateResult result) {
//        if (!request.getText().equalsIgnoreCase(result.textDst)) {
//            try {
//                Timber.d("save to local repository");
//                localRepos1itory.addTranslation(result);
//                return new DisplayTranslateResult(result, false);
//            } catch (Exception e) {
//                // somethign goes wrong while saving into db
//                e.printStackTrace();
//                return DisplayTranslateResult.createEmptyResult(request, false);
//            }
//        } else {
//            return DisplayTranslateResult.createEmptyResult(request, false);
//        }
//    }
}
