package com.andyanika.translator.network;

import com.andyanika.translator.common.RemoteRepository;
import com.andyanika.translator.common.models.AvailableLanguagesResult;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

class RemoteYandexRepository implements RemoteRepository {
    private NetworkYandexApi api;

    RemoteYandexRepository(NetworkYandexApi api) {
        this.api = api;
    }

    @Override
    public TranslateResult translate(TranslationRequest request) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new TranslateResult(request.text, String.format("%s: %s", request.language, request.text));
    }

    @Override
    public AvailableLanguagesResult getAvailableLanguages() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AvailableLanguagesResult();
    }
}
