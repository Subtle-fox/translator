package com.andyanika.translator.di.component;

import com.andyanika.datasource.local.LocalRepositoryModule;
import com.andyanika.translator.App;
import com.andyanika.translator.di.module.AppModule;
import com.andyanika.translator.di.module.NavigationModule;
import com.andyanika.translator.di.module.SchedulersModule;
import com.andyanika.translator.feature.favorites.di.FavoritesSubcomponentModule;
import com.andyanika.translator.features.main_screen.di.MainActivityComponent;
import com.andyanika.translator.features.main_screen.di.MainActivityModule;
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

        FavoritesSubcomponentModule.class
})
public interface AppComponent {
    void inject(App app);
    MainActivityComponent plus(MainActivityModule module);
}