package com.andyanika.translator.feature.translate.di;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.module.ResourceModule;
import com.andyanika.translator.feature.translate.TranslationFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@FragmentScope
@Subcomponent(modules = {
        TranslationFragmentModule.class,
        ResourceModule.class
})
public interface TranslationFragmentComponent extends AndroidInjector<TranslationFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TranslationFragment> {
        public abstract void translationModule(TranslationFragmentModule module);

        @Override
        public void seedInstance(TranslationFragment fragment) {
            translationModule(new TranslationFragmentModule(fragment));
        }
    }
}