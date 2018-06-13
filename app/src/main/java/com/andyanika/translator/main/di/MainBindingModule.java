package com.andyanika.translator.main.di;

import com.andyanika.resources.ResourceModule;
import com.andyanika.translator.common.scopes.ActivityScope;
import com.andyanika.translator.di.ViewModelFactoryModule;
import com.andyanika.translator.feature.favorites.di.FavoritesBindingModule;
import com.andyanika.translator.feature.history.di.HistoryBindingModule;
import com.andyanika.translator.feature.select.di.SelectLanguageBindingModule;
import com.andyanika.translator.feature.translate.di.TranslationBindingModule;
import com.andyanika.translator.main.MainActivity;
import com.andyanika.usecases.UseCaseModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainBindingModule {
    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            ResourceModule.class,
            ViewModelFactoryModule.class,
            UseCaseModule.class,

            FavoritesBindingModule.class,
            HistoryBindingModule.class,
            SelectLanguageBindingModule.class,
            TranslationBindingModule.class,
    })
    @ActivityScope
    MainActivity bindInjector();
}