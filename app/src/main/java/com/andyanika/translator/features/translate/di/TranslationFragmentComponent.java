package com.andyanika.translator.features.translate.di;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.translator.features.translate.TranslationFragment;
import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {TranslationFragmentModule.class})
public interface TranslationFragmentComponent {
    void inject(TranslationFragment fragment);
}