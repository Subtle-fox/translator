package com.andyanika.translator.features.translate;


import com.andyanika.translator.common.models.TranslateResult;

import io.reactivex.Observable;

public interface TranslationView {
    void showTranslation(TranslateResult result);
    void showProgress();
    void hideProgress();
    void showErrorLayout();
    void hideErrorLayout();
    void showClearBtn();
    void hideClearBtn();
    void clearResult();
    Observable<CharSequence> getSearchTextObservable();
}
