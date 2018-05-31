package com.andyanika.translator.di.module;

import android.arch.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

/**
 * Created by Andrey Kolpakov on 28.02.2018.
 * © Smart Space 2017
 */
@Module
public abstract class ViewModelFactoryModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModel(ViewModelFactory factory);
}
