package com.andyanika.translator.feature.translate.di.koin

import com.andyanika.translator.feature.translate.TranslationFragment
import com.andyanika.translator.feature.translate.TranslationPresenter
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.core.qualifier.named
import org.koin.dsl.module

val translationModule = module {
    scope<TranslationFragment> {
        scoped { PublishSubject.create<String>() }

        scoped { params ->
            TranslationPresenter(
                view = params.get(),
                translateUseCase = get(),
                getSelectedLanguagesUseCase = get(),
                selectLanguageUseCase = get(),
                retrySubject = get(),
                uiScheduler = get(named("ui")),
                computationScheduler = get(named("computation"))
            )
        }
    }
}
