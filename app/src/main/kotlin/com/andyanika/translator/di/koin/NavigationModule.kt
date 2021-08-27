package com.andyanika.translator.di.koin

import core.interfaces.ScreenRouter
import com.andyanika.translator.di.ScreenRouterImpl
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val navigationModule = module {
    val cicerone: Cicerone<Router> = Cicerone.create()

    single { cicerone.router }

    single { cicerone.getNavigatorHolder() }

    single<ScreenRouter> { ScreenRouterImpl(get()) }
}
