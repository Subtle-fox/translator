package com.andyanika.translator.di;

import com.andyanika.datasource.local.LocalRepositoryModule;
import com.andyanika.translator.App;
import com.andyanika.translator.main.di.MainActivityComponentModule;
import com.andyanika.translator.network.RemoteRepositoryModule;
import com.andyanika.usecases.UseCaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NavigationModule.class,
        LocalRepositoryModule.class,
        RemoteRepositoryModule.class,
        SchedulersModule.class,
        UseCaseModule.class,
        MainActivityComponentModule.class
})
public interface AppComponent {
    void inject(App app);
}