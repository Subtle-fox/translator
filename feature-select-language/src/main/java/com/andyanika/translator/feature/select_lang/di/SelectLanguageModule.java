package com.andyanika.translator.feature.select_lang.di;

import com.andyanika.resources.di.FragmentScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Module
public class SelectLanguageModule {
    @Provides
    @FragmentScope
    public Subject<Integer> provideObserver() {
        return PublishSubject.create();
    }
}
