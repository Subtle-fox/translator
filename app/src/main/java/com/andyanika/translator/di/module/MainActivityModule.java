package com.andyanika.translator.di.module;

import com.andyanika.translator.di.ActivityScope;
import com.andyanika.translator.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity() {
        return activity;
    }
}
