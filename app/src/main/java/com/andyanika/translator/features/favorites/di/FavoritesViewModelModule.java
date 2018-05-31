package com.andyanika.translator.features.favorites.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.di.ViewModelKey;
import com.andyanika.translator.features.favorites.FavoritesViewModel;

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
