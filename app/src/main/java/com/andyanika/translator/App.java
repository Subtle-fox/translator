package com.andyanika.translator;

import android.app.Application;
import com.andyanika.translator.di.component.AppComponent;
import com.andyanika.translator.di.component.DaggerAppComponent;
import com.andyanika.translator.di.module.AppModule;

public class App extends Application {

    private static AppComponent appComponent;

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
}
