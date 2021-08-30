package com.andyanika.usecases.di.koin

import com.andyanika.usecases.*
import core.interfaces.usecase.*
import org.koin.dsl.bind
import org.koin.dsl.module

internal val useCaseModule = module {
    factory { TranslateUseCaseImpl(get(), get()) } bind TranslationUseCase::class

    factory { SelectLanguageUseCaseImpl(get()) } bind SelectLanguageUseCase::class

    factory { GetSelectedLanguagesUseCaseImpl(get(), get()) } bind GetSelectedLanguageUseCase::class

    factory { GetLanguagesUseCaseImpl(get(), get()) } bind GetLanguagesUseCase::class

    factory { GetFavoritesUseCaseImpl(get()) } bind GetFavoritesUseCase::class

    factory { RemoveFavoriteUseCaseImpl(get()) } bind RemoveFavoriteUseCase::class

    factory { AddFavoriteUseCaseImpl(get()) } bind AddFavoriteUseCase::class

    factory { HistoryUseCaseImpl(get()) } bind HistoryUseCase::class
}
