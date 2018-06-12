package com.andyanika.translator.feature.favorites.di;

import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.ViewModelKey;
import com.andyanika.translator.feature.favorites.FavoritesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Module
abstract class FavoritesFragmentModule {
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
