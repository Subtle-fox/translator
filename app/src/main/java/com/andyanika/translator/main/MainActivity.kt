package com.andyanika.translator.main

import android.os.Bundle
import com.andyanika.resources.ResourceImpl
import com.andyanika.translator.R
import com.andyanika.translator.common.interfaces.Resources
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.getDefaultScope
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.scope.activityScope
import org.koin.androidx.scope.createScope
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainActivity : ScopeActivity() {

//    override val scope: Scope by createScope(named("x"))
    override val scope: Scope by activityScope()

    val presenter: MainActivityPresenter by inject()
    val navigatorHolder: NavigatorHolder by inject()
    val navigator: MainActivityNavigator by inject { parametersOf(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(presenter)
        if (savedInstanceState == null) {
            presenter!!.navigateToTranslation()
        }

        scope.declare(ResourceImpl(this) as Resources)
//        getKoin().scopeRegistry.rootScope.declare(ResourceImpl(this) as Resources)
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
