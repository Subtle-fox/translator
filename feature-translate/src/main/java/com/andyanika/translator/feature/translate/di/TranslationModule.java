package com.andyanika.translator.feature.translate.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.ViewModelKey;
import com.andyanika.translator.common.scopes.FragmentScope;
import com.andyanika.translator.feature.translate.TranslationViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.subjects.PublishSubject;

@Module
abstract class TranslationModule {
    @Provides
    static PublishSubject<String> provideRetrySubject() {
        return PublishSubject.create();
    }

    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(TranslationViewModel.class)
    abstract ViewModel bindViewModel(TranslationViewModel viewModel);
}
