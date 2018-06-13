package com.andyanika.translator.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
abstract class SchedulersModule {
    @Named("io")
    @Provides
    static Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Named("computation")
    @Provides
    static Scheduler provideComputationScheduler() {
        return Schedulers.computation();
    }

    @Named("ui")
    @Provides
    static Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}