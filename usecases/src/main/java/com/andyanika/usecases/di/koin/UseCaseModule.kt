package com.andyanika.usecases.di.koin

import com.andyanika.translator.common.interfaces.usecase.*
import com.andyanika.usecases.*
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule = module {
    factory { TranslateUseCaseImpl(get(), get(named("yandex")), get(named("io"))) } bind TranslationUseCase::class

    factory { SelectLanguageUseCaseImpl(get(), get(named("io"))) } bind SelectLanguageUseCase::class

    factory { params ->
        GetSelectedLanguagesUseCaseImpl(
            get(),
            get(),
            get(named("io"))
        )
    } bind GetSelectedLanguageUseCase::class

    factory {
        GetLanguagesUseCaseImpl(get(), get(), get(named("io")))
    } bind GetLanguagesUseCase::class

    factory { GetFavoritesUseCaseImpl(get(), get(named("io"))) } bind GetFavoritesUseCase::class

    factory { RemoveFavoriteUseCaseImpl(get(), get(named("io"))) } bind RemoveFavoriteUseCase::class

    factory { AddFavoriteUseCaseImpl(get(), get(named("io"))) } bind AddFavoriteUseCase::class

    factory { HistoryUseCaseImpl(get(), get(named("io"))) } bind HistoryUseCase::class
}
