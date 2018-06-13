package com.andyanika.translator.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulersModule {
    @Named("io")
    @Provides
    @Singleton
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Named("ui")
    @Provides
    @Singleton
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}