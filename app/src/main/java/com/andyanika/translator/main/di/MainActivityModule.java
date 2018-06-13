package com.andyanika.translator.main.di;

import android.content.Context;

import com.andyanika.translator.common.scopes.ActivityScope;
import com.andyanika.translator.main.MainActivity;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

@Module
abstract class MainActivityModule {
    @ActivityScope
    @Named("activity")
    @Binds
    abstract Context getActivityContext(MainActivity activity);
}
