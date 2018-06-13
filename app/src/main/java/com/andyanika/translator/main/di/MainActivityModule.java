package com.andyanika.translator.main.di;

import android.content.Context;

import com.andyanika.resources.di.ActivityScope;
import com.andyanika.translator.main.MainActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    private MainActivity activity;

    MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    @Named("activity")
    Context provideActivityContext() {
        return activity;
    }
}
