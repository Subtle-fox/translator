package com.andyanika.translator.features.translate;

import android.text.Editable;
import android.text.TextWatcher;

import javax.inject.Inject;

class TranslationTextWatcher implements TextWatcher {
    private TranslationPresenter presenter;

    @Inject
    TranslationTextWatcher(TranslationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        presenter.onTextChanged(s.toString());
    }
}
