package com.andyanika.translator.feature.favorites.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.ViewModelKey;
import com.andyanika.translator.common.scopes.FragmentScope;
import com.andyanika.translator.feature.favorites.FavoritesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

@Module
abstract class FavoritesModule {
    @Provides
    @FragmentScope
    static Subject<Integer> provideObserver() {
        return PublishSubject.create();
    }

    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(FavoritesViewModel.class)
    abstract ViewModel bindViewModel(FavoritesViewModel viewModel);
}
