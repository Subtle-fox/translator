package com.andyanika.translator.common;

import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import java.io.IOException;

import io.reactivex.Observable;

public interface RemoteRepository {
    Observable<TranslateResult> translate(TranslationRequest request);
}
