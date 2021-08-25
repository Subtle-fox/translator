package com.andyanika.translator.di.koin

import com.andyanika.resources.ResourceImpl
import com.andyanika.translator.common.interfaces.Resources
import com.andyanika.translator.main.MainActivity
import com.andyanika.translator.main.MainActivityNavigator
import com.andyanika.translator.main.MainActivityPresenter
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.scoped

val activityModule = module {
    single { (activity: MainActivity) -> MainActivityNavigator(activity) }

    scope<MainActivity> {
        scoped { (activity: MainActivity) -> ResourceImpl(activity) } bind Resources::class

        scoped<MainActivityPresenter>()
    }
}
