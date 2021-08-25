package com.andyanika.translator.main

import com.andyanika.translator.feature.favorites.FavoriteFragment
import com.andyanika.translator.feature.history.HistoryFragment
import com.andyanika.translator.feature.select.SelectLanguageFragment
import com.andyanika.translator.feature.translate.TranslationFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Translation() = FragmentScreen { TranslationFragment() }
    fun History() = FragmentScreen { HistoryFragment() }
    fun Favorite() = FragmentScreen { FavoriteFragment() }
    fun SelectLanguage(mode: String) = FragmentScreen { SelectLanguageFragment.create(mode) }
}
