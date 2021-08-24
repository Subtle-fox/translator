package com.andyanika.translator.repository.remote;

import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.TranslateResult;

import javax.inject.Inject;

class YandexModelsAdapter {
    @Inject
    YandexModelsAdapter() {
    }

    TranslateResult convert(TranslateRequest request, YandexTranslationResponse response) {
        StringBuilder res = new StringBuilder();
        for (String s : response.translatedText) {
            res.append(s).append('\n');
        }

        return new TranslateResult(request.getText(), res.toString().trim(), request.getDirection());
    }

    // Yandex's specific case:
    boolean isTranslationFound(TranslateResult result) {
        return !result.textDst.isEmpty() && !result.textSrc.equalsIgnoreCase(result.textDst);
    }
}
