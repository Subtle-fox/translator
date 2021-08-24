package com.andyanika.translator.main

import android.os.Bundle
import com.andyanika.translator.R
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @JvmField
    @Inject
    var presenter: MainActivityPresenter? = null

    @JvmField
    @Inject
    var navigatorHolder: NavigatorHolder? = null

    @JvmField
    @Inject
    var navigator: MainActivityNavigator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(presenter)
        if (savedInstanceState == null) {
            presenter!!.navigateToTranslation()
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder!!.setNavigator(navigator!!)
    }

    override fun onPause() {
        navigatorHolder!!.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        navigator!!.back()
    }

    val fragmentContainerId: Int
        get() = R.id.content_frame
}
