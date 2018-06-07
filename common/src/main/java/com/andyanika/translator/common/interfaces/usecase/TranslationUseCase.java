package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.TranslateResult;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface TranslationUseCase {
    Observable<TranslateResult> run(@NonNull String srcText);
}
