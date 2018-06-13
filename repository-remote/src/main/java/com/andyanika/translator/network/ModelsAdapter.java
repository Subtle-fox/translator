package com.andyanika.translator.network;

import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

public class ModelsAdapter {
    TranslateResult convert(TranslationRequest request, TranslationResponse response) {
        StringBuilder res = new StringBuilder();
        for (String s : response.translatedText) {
            res.append(s).append('\n');
        }

        return new TranslateResult(request.text, res.toString().trim(), request.direction, false);
    }
}
