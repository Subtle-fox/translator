package com.andyanika.translator.di.component;

import com.andyanika.datasource.local.LocalRepositoryModule;
import com.andyanika.translator.App;
import com.andyanika.translator.di.module.AppModule;
import com.andyanika.translator.features.main_screen.di.MainActivityModule;
import com.andyanika.translator.di.module.NavigationModule;
import com.andyanika.translator.features.main_screen.di.MainActivityComponent;
import com.andyanika.translator.network.RemoteRepositoryModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AppModule.class,
        NavigationModule.class,
        LocalRepositoryModule.class,
        RemoteRepositoryModule.class
})
public interface AppComponent {
    void inject(App app);
    MainActivityComponent plus(MainActivityModule module);
}