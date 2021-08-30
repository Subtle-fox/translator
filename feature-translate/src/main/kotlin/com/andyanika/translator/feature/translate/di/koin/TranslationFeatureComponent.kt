package com.andyanika.translator.feature.translate.di.koin

import org.koin.core.Koin
import org.koin.core.component.KoinComponent

class TranslationFeatureComponent : KoinComponent {
    override fun getKoin(): Koin {
        return TranslationFeatureContext.koinApplication.koin
    }
}
