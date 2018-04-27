package com.andyanika.translator.ui.view;


import com.andyanika.translator.common.models.TranslateResult;

public interface TranslationView {
    void showTranslation(TranslateResult result);
    void showProgress();
    void hideProgress();
    void showErrorLayout();
    void hideErrorLayout();
}
