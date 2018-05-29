package com.andyanika.translator.di.module;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.select_lang.SelectLanguageView;

import dagger.Module;
import dagger.Provides;

@Module
public class SelectLanguageModule {
    private SelectLanguageView view;

    public SelectLanguageModule(SelectLanguageView view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    public SelectLanguageView getView() {
        return view;
    }
}
