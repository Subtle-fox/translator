package com.andyanika.translator.feature.translate.di

import com.andyanika.translator.common.scopes.FragmentScope
import com.andyanika.translator.feature.translate.TranslationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface TranslationBindingModule {
    @ContributesAndroidInjector(modules = [TranslationModule::class])
    @FragmentScope
    fun bindInjector(): TranslationFragment
}
