package com.andyanika.translator.di;

import com.andyanika.datasource.local.LocalRepositoryModule;
import com.andyanika.translator.App;
import com.andyanika.translator.main.di.MainActivityComponentModule;
import com.andyanika.translator.repository.remote.RemoteRepositoryComponent;
import com.andyanika.usecases.UseCaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NavigationModule.class,
        LocalRepositoryModule.class,
        SchedulersModule.class,
        UseCaseModule.class,
        MainActivityComponentModule.class
}, dependencies = {
        RemoteRepositoryComponent.class
})
public interface AppComponent {
    void inject(App app);
}