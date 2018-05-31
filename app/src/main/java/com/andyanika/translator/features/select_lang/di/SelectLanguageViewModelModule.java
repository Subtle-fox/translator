package com.andyanika.translator.features.select_lang.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.di.ViewModelKey;
import com.andyanika.translator.features.select_lang.SelectLanguageViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SelectLanguageViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(SelectLanguageViewModel.class)
    abstract ViewModel bindViewModel(SelectLanguageViewModel viewModel);
}
