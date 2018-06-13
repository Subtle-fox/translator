package com.andyanika.translator.feature.favorites.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.ViewModelKey;
import com.andyanika.translator.feature.favorites.FavoritesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class FavoritesViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(FavoritesViewModel.class)
    abstract ViewModel bindViewModel(FavoritesViewModel viewModel);
}
