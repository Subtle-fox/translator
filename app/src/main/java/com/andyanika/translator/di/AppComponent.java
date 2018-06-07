package com.andyanika.translator.di;

import com.andyanika.datasource.local.LocalRepositoryModule;
import com.andyanika.translator.App;
import com.andyanika.translator.feature.favorites.di.FavoritesComponentModule;
import com.andyanika.translator.feature.history.di.HistoryComponentModule;
import com.andyanika.translator.feature.select_lang.di.SelectLanguageComponentModule;
import com.andyanika.translator.feature.translate.di.TranslationComponentModule;
import com.andyanika.translator.main.di.MainActivityComponent;
import com.andyanika.translator.main.di.MainActivityModule;
import com.andyanika.translator.main.di.NavigationModule;
import com.andyanika.translator.network.RemoteRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NavigationModule.class,
        LocalRepositoryModule.class,
        RemoteRepositoryModule.class,
        SchedulersModule.class,

        FavoritesComponentModule.class,
        SelectLanguageComponentModule.class,
        HistoryComponentModule.class,
        TranslationComponentModule.class
})
public interface AppComponent {
    void inject(App app);
    MainActivityComponent plus(MainActivityModule module);
}