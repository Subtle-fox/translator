package com.andyanika.translator.feature.history.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.ViewModelKey;
import com.andyanika.translator.feature.history.HistoryViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class HistoryViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindViewModel(HistoryViewModel viewModel);
}