package com.andyanika.translator.feature.translate;


import com.andyanika.translator.common.models.TranslateResult;

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
    void setSrcLabel(String text);
    void setDstLabel(String text);
    void showOffline();
    void hideOffline();
}
