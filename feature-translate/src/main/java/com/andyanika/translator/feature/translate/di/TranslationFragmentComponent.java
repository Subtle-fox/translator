package com.andyanika.translator.feature.translate.di;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.translator.feature.translate.TranslationFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@FragmentScope
@Subcomponent(modules = {
        TranslationFragmentModule.class,
})
public interface TranslationFragmentComponent extends AndroidInjector<TranslationFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TranslationFragment> {

    }
}