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
    void clearTranslation();
    Observable<CharSequence> getSearchTextObservable();
    void setSrcLabel(String text);
    void setDstLabel(String text);
    void showOffline();
    void hideOffline();
}
