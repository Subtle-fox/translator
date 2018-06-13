package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.UiLanguageModel;

import java.util.List;

import io.reactivex.Observable;

public interface GetLanguagesUseCase {
    Observable<List<UiLanguageModel>> run(boolean selectSource);
}
