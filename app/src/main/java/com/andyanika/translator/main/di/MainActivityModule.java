package com.andyanika.translator.main.di;

import com.andyanika.resources.di.ActivityScope;
import com.andyanika.translator.main.MainActivity;

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
