package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.DirectionPair;

import io.reactivex.Observable;

public interface GetSelectedLanguageUseCase {
    Observable<DirectionPair<String>> run();
}
