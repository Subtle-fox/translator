package com.andyanika.translator.feature.select.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.ViewModelKey;
import com.andyanika.translator.common.scopes.FragmentScope;
import com.andyanika.translator.feature.select.SelectLanguageViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Module
abstract class SelectLanguageModule {
    @Provides
    @FragmentScope
    static Subject<Integer> provideObserver() {
        return PublishSubject.create();
    }

    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(SelectLanguageViewModel.class)
    abstract ViewModel bindViewModel(SelectLanguageViewModel viewModel);
}
