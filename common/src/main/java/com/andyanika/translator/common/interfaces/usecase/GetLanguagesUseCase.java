package com.andyanika.translator.common.interfaces.usecase;

import com.andyanika.translator.common.models.ui.DisplayLanguageModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public interface GetLanguagesUseCase {
    Observable<List<DisplayLanguageModel>> run(boolean selectSource);
}
