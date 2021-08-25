package com.andyanika.translator.di

import com.andyanika.translator.common.interfaces.ScreenRouter
import com.andyanika.translator.common.scopes.ApplicationScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
//import dagger.Module
//import dagger.Provides

/*
@Module
class NavigationModule internal constructor() {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @ApplicationScope
    fun provideCiceroneRouter(): Router {
        return cicerone.router
    }

    @Provides
    @ApplicationScope
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Provides
    @ApplicationScope
    fun provideScreenRouter(router: Router?): ScreenRouter {
        return ScreenRouterImpl(router!!)
    }

}
*/
