package com.andyanika.translator.features.favorites.di;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.favorites.FavoriteFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {
        FavoriteFragmentModule.class,
        FavoritesViewModelModule.class
})
public interface FavoriteFragmentComponent {
    void inject(FavoriteFragment fragment);
}