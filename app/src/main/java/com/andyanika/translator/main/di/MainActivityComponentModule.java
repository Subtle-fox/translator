package com.andyanika.translator.main.di;

import android.app.Activity;

import com.andyanika.translator.main.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = MainActivityComponent.class)
public abstract class MainActivityComponentModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindFragmentInjectorFactory(MainActivityComponent.Builder builder);
}
