package com.andyanika.translator.di;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context appContext;

    public AppModule(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    @Provides
    @Singleton
    @Named("app")
    Context provideAppContext() {
        return appContext;
    }
}
