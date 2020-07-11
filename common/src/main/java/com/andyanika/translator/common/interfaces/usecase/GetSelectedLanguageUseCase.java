package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.TranslateDirection;

import io.reactivex.rxjava3.core.Observable;


public interface GetSelectedLanguageUseCase {
    Observable<TranslateDirection<String>> run();
}
