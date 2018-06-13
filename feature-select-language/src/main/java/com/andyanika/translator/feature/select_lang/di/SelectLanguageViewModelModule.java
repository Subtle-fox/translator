package com.andyanika.translator.feature.select_lang.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.ViewModelKey;
import com.andyanika.translator.feature.select_lang.SelectLanguageViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class SelectLanguageViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(SelectLanguageViewModel.class)
    abstract ViewModel bindViewModel(SelectLanguageViewModel viewModel);
}
