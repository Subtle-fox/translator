package com.andyanika.translator.feature.favorites.di;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.module.ViewModelFactoryModule;
import com.andyanika.translator.feature.favorites.FavoriteFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FavoritesBindingModule {
    @ContributesAndroidInjector(modules = {
            FavoritesFragmentModule.class,
            FavoritesViewModelModule.class,
            ViewModelFactoryModule.class
    })
    @FragmentScope
    FavoriteFragment bindFragmentInjectorFactory();
}