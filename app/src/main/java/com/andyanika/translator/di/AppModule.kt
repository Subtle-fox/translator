package com.andyanika.translator.di;

import android.content.Context;

import com.andyanika.translator.App;
import com.andyanika.translator.common.scopes.ApplicationScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {
    @Binds
    @ApplicationScope
    @Named("app")
    public abstract Context provideAppContext(App app);
}
