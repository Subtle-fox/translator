package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.ui.DisplayTranslateResult;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface TranslationUseCase {
    Observable<DisplayTranslateResult> run(@NonNull String srcText);
}
