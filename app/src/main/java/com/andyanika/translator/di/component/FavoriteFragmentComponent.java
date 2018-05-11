package com.andyanika.translator.di.component;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.di.module.FavoriteFragmentModule;
import com.andyanika.translator.features.favorites.FavoriteFragment;
import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {FavoriteFragmentModule.class})
public interface FavoriteFragmentComponent {
    void inject(FavoriteFragment fragment);
}