package com.andyanika.translator;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.andyanika.translator.di.AppComponent;
import com.andyanika.translator.di.AppModule;
import com.andyanika.translator.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector {

    private static AppComponent appComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = buildComponent();
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

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
