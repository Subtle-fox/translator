package com.andyanika.translator.di.component;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.di.module.TranslationFragmentModule;
import com.andyanika.translator.features.translate.TranslationFragment;
import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {TranslationFragmentModule.class})
public interface TranslationFragmentComponent {
    void inject(TranslationFragment fragment);
}