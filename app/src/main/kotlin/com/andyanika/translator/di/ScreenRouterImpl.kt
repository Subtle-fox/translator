package com.andyanika.translator.di

import com.github.terrakok.cicerone.Router
import core.constants.Screens
import core.interfaces.ScreenRouter
import com.andyanika.translator.main.Screens as ScreenFragments

internal class ScreenRouterImpl internal constructor(private val router: Router) : ScreenRouter {
    override fun navigateTo(screenKey: String, args: Any) {
        router.navigateTo(getScreenByKey(screenKey, args))
    }

    override fun backTo(screenKey: String) {
        router.backTo(getScreenByKey(screenKey))
    }

    private fun getScreenByKey(screenKey: String, args: Any? = null) = when (screenKey) {
        Screens.TRANSLATION -> ScreenFragments.Translation()
        Screens.HISTORY -> ScreenFragments.History()
        Screens.FAVORITES -> ScreenFragments.Favorite()
        Screens.SELECT_LANGUAGE -> ScreenFragments.SelectLanguage(args as String)
        else -> throw IllegalAccessException(screenKey)
    }
}
