package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.TranslateDirection;

import io.reactivex.Observable;

public interface GetSelectedLanguageUseCase {
    Observable<TranslateDirection<String>> run();
}
