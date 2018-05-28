package com.andyanika.translator.di.component;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.di.module.SelectLanguageModule;
import com.andyanika.translator.features.select_lang.SelectLanguageFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {SelectLanguageModule.class})
public interface SelectLanguageComponent {
    void inject(SelectLanguageFragment fragment);
}