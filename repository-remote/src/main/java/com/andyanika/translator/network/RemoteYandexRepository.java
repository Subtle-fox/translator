package com.andyanika.translator.network;

import com.andyanika.translator.common.RemoteRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

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
        this.key = "trnsl.1.1.20180425T102131Z.2f89797675600432.d96e99dc90d3a48a0db9128c85bddfaeea265ef4";
    }

    @Override
    public Observable<TranslateResult> translate(TranslationRequest request) {
        String direction = directionBuilder.buildParam(request.direction);
        return api.translate(key, request.text, direction).map(response -> modelsAdapter.convert(request, response));
    }

//    @Override
//    public TranslateResult translate(TranslationRequest request) throws IOException {
//        String direction = directionBuilder.buildParam(request.direction);
//        Call<TranslationResponse> translate = api.translate(key, request.text, direction);
//        TranslationResponse response = parseResult(translate.execute());
//        return modelsAdapter.convert(request, response);
//    }
//
//    @Override
//    public AvailableLanguagesResult getAvailableLanguages(LanguageCode languageCode) throws IOException {
//        Response<ResponseBody> response = api.getAvailableLanguages(key, languageCode.toString().toLowerCase()).execute();
//        parseResult(response);
//        return new AvailableLanguagesResult();
//    }
//
//    private <T> T parseResult(Response<T> response) throws IOException {
//        if (response.isSuccessful()) {
//            return response.body();
//        } else {
//            throw new IOException(response.message());
//        }

}
