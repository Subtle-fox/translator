package com.andyanika.resources.di;

import com.andyanika.translator.common.scopes.ActivityScope;
import com.andyanika.translator.common.interfaces.Resources;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ResourceModule {
    @ActivityScope
    @Binds
    abstract Resources provideResources(ResourceImpl resource);
}
