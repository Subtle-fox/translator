package com.andyanika.translator;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.andyanika.translator.di.component.AppComponent;
import com.andyanika.translator.di.component.DaggerAppComponent;
import com.andyanika.translator.di.module.AppModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class App extends Application implements HasSupportFragmentInjector {

    private static AppComponent appComponent;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = buildComponent();
        appComponent.inject(this);
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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingActivityInjector;
    }
}
