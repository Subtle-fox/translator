package com.andyanika.translator

import android.app.Application
import android.util.Log
import com.andyanika.resources.di.koin.resourceModule
import com.andyanika.translator.di.koin.activityModule
import com.andyanika.translator.di.koin.dispatchersModule
import com.andyanika.translator.di.koin.navigationModule
import com.andyanika.translator.feature.favorites.di.koin.favoritesModule
import com.andyanika.translator.feature.history.di.koin.historyModule
import com.andyanika.translator.feature.select.di.koin.selectLanguageModule
import com.andyanika.translator.feature.translate.di.koin.translationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
        initKoin()
    }

    private fun initLogger() {
        val tree = if (BuildConfig.DEBUG) DebugTree() else object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (priority >= Log.ERROR) {
                    System.err.println("Timber >>> $message")
                }
            }
        }
        Timber.plant(tree)
        Timber.d("logger initialized")
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            androidLogger(level = Level.INFO)
            modules(
                navigationModule,
                dispatchersModule,

                resourceModule,

                activityModule,
                translationModule,
                selectLanguageModule,
                historyModule,
                favoritesModule,
            )
        }
    }
}
