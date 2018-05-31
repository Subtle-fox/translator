package com.andyanika.translator.features.favorites.di;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.favorites.FavoriteView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Module
public class FavoriteFragmentModule {
    @Provides
    @FragmentScope
    public Subject<Integer> provideObserver() {
        return PublishSubject.create();
    }
}
