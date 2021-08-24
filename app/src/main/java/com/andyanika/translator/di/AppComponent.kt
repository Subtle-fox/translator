package com.andyanika.translator.di;

import com.andyanika.translator.App;
import com.andyanika.translator.common.scopes.ApplicationScope;
import com.andyanika.translator.main.di.MainBindingModule;
import com.andyanika.translator.repository.local.LocalRepositoryModule;
import com.andyanika.translator.repository.remote.RemoteRepositoryComponent;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@ApplicationScope
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        NavigationModule.class,
        LocalRepositoryModule.class,
        SchedulersModule.class,
        MainBindingModule.class,
}, dependencies = {
        RemoteRepositoryComponent.class,
})
public interface AppComponent extends AndroidInjector<App> {
    void inject(App app);

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
        public abstract Builder remoteRepositoryComponent(RemoteRepositoryComponent component);
    }
}