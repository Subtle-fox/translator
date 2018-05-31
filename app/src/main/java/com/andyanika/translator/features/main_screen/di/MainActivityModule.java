package com.andyanika.translator.features.main_screen.di;

import com.andyanika.translator.di.ActivityScope;
import com.andyanika.translator.features.main_screen.MainActivity;

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
