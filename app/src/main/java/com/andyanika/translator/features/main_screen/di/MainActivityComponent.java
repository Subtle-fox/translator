package com.andyanika.translator.features.main_screen.di;

import com.andyanika.resources.di.ActivityScope;
import com.andyanika.translator.di.module.ViewModelFactoryModule;
import com.andyanika.translator.features.main_screen.MainActivity;
import com.andyanika.translator.features.translate.di.TranslationFragmentComponent;
import com.andyanika.translator.features.translate.di.TranslationFragmentModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        MainActivityModule.class,
        ViewModelFactoryModule.class
})
public interface MainActivityComponent {
    void inject(MainActivity activity);
    TranslationFragmentComponent plus(TranslationFragmentModule module);
}