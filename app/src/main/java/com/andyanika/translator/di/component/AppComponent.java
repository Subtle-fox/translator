package com.andyanika.translator.di.component;

import com.andyanika.datasource.local.LocalRepositoryModule;
import com.andyanika.translator.App;
import com.andyanika.translator.di.module.AppModule;
import com.andyanika.translator.di.module.MainActivityModule;
import com.andyanika.translator.network.RemoteRepositoryModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AppModule.class,
        LocalRepositoryModule.class,
        RemoteRepositoryModule.class
})
public interface AppComponent {
    void inject(App app);
    MainActivityComponent plus(MainActivityModule module);
}