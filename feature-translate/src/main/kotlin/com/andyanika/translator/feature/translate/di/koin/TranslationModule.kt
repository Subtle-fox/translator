package com.andyanika.translator.feature.translate.di.koin

import com.andyanika.translator.feature.translate.TranslationFragment
import com.andyanika.translator.feature.translate.mvi.TranslationFeature
import org.koin.dsl.module
import org.koin.dsl.scoped

val translationModule = module {
    scope<TranslationFragment> {
        // NOTE: as a variant: to bind to fragment's lifecycle
//        scope<TranslationFeature> {
//            scoped<TranslationFeature>()
//        }
    }

    scope<TranslationFeature> {
        scoped<TranslationFeature>()
    }
}
