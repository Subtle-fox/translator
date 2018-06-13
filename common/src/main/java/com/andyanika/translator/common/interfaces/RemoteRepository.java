package com.andyanika.translator.common.interfaces;

import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.TranslateResult;

import io.reactivex.Observable;

public interface RemoteRepository {
    Observable<TranslateResult> translate(TranslateRequest request);
}
