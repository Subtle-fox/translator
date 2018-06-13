package com.andyanika.translator.main.di;

import com.andyanika.resources.di.ActivityScope;
import com.andyanika.resources.di.module.ResourceModule;
import com.andyanika.translator.feature.favorites.di.FavoritesComponentModule;
import com.andyanika.translator.feature.history.di.HistoryComponentModule;
import com.andyanika.translator.feature.select_lang.di.SelectLanguageComponentModule;
import com.andyanika.translator.feature.translate.di.TranslationComponentModule;
import com.andyanika.translator.main.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {
        MainActivityModule.class,
        ResourceModule.class,

        FavoritesComponentModule.class,
        SelectLanguageComponentModule.class,
        HistoryComponentModule.class,
        TranslationComponentModule.class
})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
        public abstract void translationModule(MainActivityModule module);

        @Override
        public void seedInstance(MainActivity activity) {
            translationModule(new MainActivityModule(activity));
        }
    }

//    Resources provideResource();
}