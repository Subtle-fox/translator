package com.andyanika.translator.di.module;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.select_lang.SelectLanguageListAdapter;
import com.andyanika.translator.features.select_lang.SelectLanguageView;
import com.andyanika.usecases.SelectLanguageUseCase;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Router;

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
