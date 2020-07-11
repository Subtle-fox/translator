package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.LanguageCode;

import io.reactivex.rxjava3.core.Completable;

public interface SelectLanguageUseCase {
    Completable setSrc(LanguageCode code);

    Completable setDst(LanguageCode code);

    Completable swap();
}
