package com.andyanika.translator.feature.favorites.di;

import android.support.v4.app.Fragment;

import com.andyanika.translator.feature.favorites.FavoriteFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = FavoriteFragmentComponent.class)
public abstract class FavoritesComponentModule {
    @Binds
    @IntoMap
    @FragmentKey(FavoriteFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment>
    bindFragmentInjectorFactory(FavoriteFragmentComponent.Builder builder);
}