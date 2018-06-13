package com.andyanika.translator.feature.translate.di;

import com.andyanika.translator.common.scopes.FragmentScope;
import com.andyanika.translator.feature.translate.TranslationFragment;
import com.andyanika.translator.feature.translate.TranslationView;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module
abstract class TranslationModule {
    @Provides
    static PublishSubject<String> provideRetrySubject() {
        return PublishSubject.create();
    }

    @Binds
    @FragmentScope
    abstract TranslationView getView(TranslationFragment fragment);
}
