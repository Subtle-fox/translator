package com.andyanika.translator.feature.translate.di;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.translator.feature.translate.TranslationFragment;
import com.andyanika.translator.feature.translate.TranslationView;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;

@Module(includes = TranslationFragmentModule.Declaration.class)
public class TranslationFragmentModule {
    @Module
    public interface Declaration {
        @Binds
        @FragmentScope
        TranslationView getView(TranslationFragment fragment);
    }

    @Provides
    public PublishSubject<String> provideRetrySubject() {
        return PublishSubject.create();
    }
}
