package com.andyanika.translator.di;

import com.andyanika.translator.App;
import com.andyanika.translator.common.scopes.ApplicationScope;
import com.andyanika.translator.main.di.MainBindingModule;
import com.andyanika.translator.repository.local.LocalRepositoryModule;
import com.andyanika.translator.repository.remote.RemoteRepositoryComponent;

import dagger.Component;

@ApplicationScope
@Component(modules = {
        AppModule.class,
        NavigationModule.class,
        LocalRepositoryModule.class,
        SchedulersModule.class,
        MainBindingModule.class,
}, dependencies = {
        RemoteRepositoryComponent.class,
})
public interface AppComponent {
    void inject(App app);
}