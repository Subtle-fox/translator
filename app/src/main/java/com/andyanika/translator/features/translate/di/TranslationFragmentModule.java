package com.andyanika.translator.features.translate.di;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.translate.TranslationView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module
public class TranslationFragmentModule {
    private TranslationView view;

    public TranslationFragmentModule(TranslationView view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    public TranslationView getView() {
        return view;
    }

    @Provides
    @FragmentScope
    public PublishSubject<String> provideRetrySubject() {
        return PublishSubject.create();
    }
}
