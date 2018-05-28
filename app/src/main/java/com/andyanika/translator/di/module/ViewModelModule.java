package com.andyanika.translator.di.module;

import android.arch.lifecycle.ViewModel;

import com.andyanika.translator.di.ViewModelKey;
import com.andyanika.translator.features.favorites.FavoritesViewModel;
import com.andyanika.translator.features.history.HistoryViewModel;
import com.andyanika.translator.features.select_lang.SelectLanguageViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryViewModel(HistoryViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel.class)
    abstract ViewModel bindFavoritesViewModel(FavoritesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectLanguageViewModel.class)
    abstract ViewModel bindSelectLanguageViewModel(SelectLanguageViewModel viewModel);
}
