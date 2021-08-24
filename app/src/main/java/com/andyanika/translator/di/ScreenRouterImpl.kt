package com.andyanika.translator.di

import com.andyanika.translator.common.constants.Screens
import com.andyanika.translator.common.interfaces.ScreenRouter
import com.github.terrakok.cicerone.Router
import com.andyanika.translator.main.Screens as ScreenFragments

class ScreenRouterImpl internal constructor(private val router: Router) : ScreenRouter {
    override fun navigateTo(screenKey: String, args: Any) {
        router.navigateTo(getScreenByKey(screenKey, args))
    }

    override fun backTo(screenKey: String) {
        router.backTo(getScreenByKey(screenKey))
    }

    fun getScreenByKey(screenKey: String, args: Any? = null) = when (screenKey) {
        Screens.TRANSLATION -> ScreenFragments.Translation()
        Screens.HISTORY -> ScreenFragments.History()
        Screens.FAVORITES -> ScreenFragments.Favorite()
        Screens.SELECT_LANGUAGE -> ScreenFragments.SelectLanguage(args as String)
        else -> throw IllegalAccessException(screenKey)
    }
}
