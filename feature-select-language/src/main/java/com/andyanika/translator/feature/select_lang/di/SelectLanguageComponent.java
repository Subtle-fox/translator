package com.andyanika.translator.feature.select_lang.di;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.module.ViewModelFactoryModule;
import com.andyanika.translator.feature.select_lang.SelectLanguageFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@FragmentScope
@Subcomponent(modules = {
        SelectLanguageModule.class,
        SelectLanguageViewModelModule.class,
        ViewModelFactoryModule.class
})
public interface SelectLanguageComponent extends AndroidInjector<SelectLanguageFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SelectLanguageFragment> {

    }
}