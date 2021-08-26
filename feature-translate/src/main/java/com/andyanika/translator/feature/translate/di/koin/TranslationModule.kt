package com.andyanika.translator.feature.translate.di.koin

import com.andyanika.translator.feature.translate.TranslationFragment
import com.andyanika.translator.feature.translate.TranslationPresenter
import org.koin.dsl.module

val translationModule = module {
    scope<TranslationFragment> {
        scoped { params ->
            TranslationPresenter(
                view = params.get(),
                translateUseCase = get(),
                getSelectedLanguagesUseCase = get(),
                selectLanguageUseCase = get(),
            )
        }
    }
}
