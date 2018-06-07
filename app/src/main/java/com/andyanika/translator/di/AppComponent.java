package com.andyanika.translator.di;

import com.andyanika.datasource.local.LocalRepositoryModule;
import com.andyanika.translator.App;
import com.andyanika.translator.main.di.MainActivityComponentModule;
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
        MainActivityComponentModule.class,
        UseCaseModule.class
})
public interface AppComponent {
    void inject(App app);
}