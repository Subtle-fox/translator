package com.andyanika.translator.main

import android.view.MenuItem
import com.andyanika.translator.R
import com.andyanika.translator.main.Screens.Favorite
import com.andyanika.translator.main.Screens.History
import com.andyanika.translator.main.Screens.Translation
import com.github.terrakok.cicerone.Router
import com.google.android.material.bottomnavigation.BottomNavigationView

internal class MainActivityPresenter internal constructor(private val router: Router) :
    BottomNavigationView.OnNavigationItemSelectedListener {
    fun navigateToTranslation() {
        router.navigateTo(Translation())
    }

    fun navigateToHistory() {
        router.replaceScreen(History())
    }

    fun navigateToFavorites() {
        router.replaceScreen(Favorite())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_translate -> {
                navigateToTranslation()
                return true
            }
            R.id.navigation_history -> {
                navigateToHistory()
                return true
            }
            R.id.navigation_favorite -> {
                navigateToFavorites()
                return true
            }
        }
        return false
    }
}
