package com.andyanika.translator;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.andyanika.translator.di.AppComponent;
import com.andyanika.translator.di.AppModule;
import com.andyanika.translator.di.DaggerAppComponent;
import com.andyanika.translator.repository.remote.DaggerRemoteRepositoryComponent;
import com.andyanika.translator.repository.remote.RemoteRepositoryComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = buildComponent();
        appComponent.inject(this);
        initLogger();
    }

    private void initLogger() {
        Timber.Tree tree = BuildConfig.DEBUG
                ? new Timber.DebugTree()
                : new Timber.Tree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
                if (priority >= Log.ERROR) {
                    System.err.println("Timber >>> " + message);
                }
            }
        };
        Timber.plant(tree);
        Timber.d("logger initialized");
    }

    private AppComponent buildComponent() {
        RemoteRepositoryComponent remoteRepositoryComponent = DaggerRemoteRepositoryComponent
                .builder()
                .build();

        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .remoteRepositoryComponent(remoteRepositoryComponent)
                .build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
