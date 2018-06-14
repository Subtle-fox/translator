package com.andyanika.translator;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andyanika.translator.di.DaggerAppComponent;
import com.andyanika.translator.repository.remote.DaggerRemoteRepositoryComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent
                .builder()
                .remoteRepositoryComponent(DaggerRemoteRepositoryComponent.create())
                .create(this);
    }

    private void initLogger() {
        Timber.Tree tree = BuildConfig.DEBUG
                ? new Timber.DebugTree()
                : new Timber.Tree() {
            @Override
            protected void log(int priority, String tag, @NonNull String message, Throwable t) {
                if (priority >= Log.ERROR) {
                    System.err.println("Timber >>> " + message);
                }
            }
        };
        Timber.plant(tree);
        Timber.d("logger initialized");
    }
}
