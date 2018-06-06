package com.andyanika.translator.feature.favorites.di;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.module.ViewModelFactoryModule;
import com.andyanika.translator.feature.favorites.FavoriteFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@FragmentScope
@Subcomponent(modules = {
        FavoriteFragmentModule.class,
        FavoritesViewModelModule.class,
        ViewModelFactoryModule.class
})
public interface FavoriteFragmentComponent extends AndroidInjector<FavoriteFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FavoriteFragment> {

    }
}