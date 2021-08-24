package com.andyanika.translator.feature.translate.di

import com.andyanika.translator.common.scopes.FragmentScope
import com.andyanika.translator.feature.translate.TranslationFragment
import com.andyanika.translator.feature.translate.TranslationView
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.subjects.PublishSubject

@Module
internal interface TranslationModule {
    @Binds
    @FragmentScope
    fun getView(fragment: TranslationFragment): TranslationView

    companion object {
        @Provides
        fun provideRetrySubject(): PublishSubject<String> {
            return PublishSubject.create()
        }
    }
}
