package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.ui.DisplayTranslateResult;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;


public interface TranslationUseCase {
    Observable<DisplayTranslateResult> run(@NonNull String srcText);
}
