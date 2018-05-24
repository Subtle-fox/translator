package com.andyanika.translator.features.translate;


import com.andyanika.translator.common.models.LanguageDescription;
import com.andyanika.translator.common.models.TranslateResult;

public interface TranslationView {
    void showTranslation(TranslateResult result);
    void showProgress();
    void hideProgress();
    void showErrorLayout();
    void hideErrorLayout();
    void setSrcLabel(String text);
    void setDstLabel(String text);
}