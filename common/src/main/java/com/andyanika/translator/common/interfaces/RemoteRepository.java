package com.andyanika.translator.common.interfaces;

import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import io.reactivex.Observable;

public interface RemoteRepository {
    Observable<TranslateResult> translate(TranslationRequest request);
}
