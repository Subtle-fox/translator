package com.andyanika.translator.features.select_lang.di;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.select_lang.SelectLanguageFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {
        SelectLanguageModule.class,
        SelectLanguageViewModelModule.class
})
public interface SelectLanguageComponent {
    void inject(SelectLanguageFragment fragment);
}