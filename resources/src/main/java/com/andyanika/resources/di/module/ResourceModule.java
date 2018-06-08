package com.andyanika.resources.di.module;

import com.andyanika.resources.di.ActivityScope;
import com.andyanika.translator.common.interfaces.Resources;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ResourceModule {
    @ActivityScope
    @Binds
    abstract Resources provideResources(ResourceImpl resource);
}
