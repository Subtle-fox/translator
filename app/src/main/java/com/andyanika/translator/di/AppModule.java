package com.andyanika.translator.di;

import android.content.Context;

import com.andyanika.translator.common.scopes.ApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context appContext;

    public AppModule(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    @Named("app")
    Context provideAppContext() {
        return appContext;
    }
}
