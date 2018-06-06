package com.andyanika.translator.di;

import com.andyanika.resources.di.ActivityScope;
import com.andyanika.resources.di.module.ViewModelFactoryModule;
import com.andyanika.translator.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        MainActivityModule.class,
        ViewModelFactoryModule.class
})
public interface MainActivityComponent {
    void inject(MainActivity activity);
}