package com.andyanika.translator

import android.app.Application
import android.util.Log
import com.andyanika.resources.di.koin.resourceModule
import com.andyanika.translator.di.koin.activityModule
import com.andyanika.translator.di.koin.dispatchersModule
import com.andyanika.translator.di.koin.navigationModule
import com.andyanika.translator.di.koin.schedulersModule
import com.andyanika.translator.feature.select.di.koin.selectLanguageModule
import com.andyanika.translator.feature.translate.di.koin.translationModule
import com.andyanika.translator.repository.local.di.koin.localRepositoryModule
import com.andyanika.translator.repository.remote.di.koin.networkModule
import com.andyanika.usecases.di.koin.useCaseModule
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
            androidLogger(level = Level.DEBUG)
            modules(
                navigationModule,
                schedulersModule,
                dispatchersModule,

                localRepositoryModule,
                networkModule,
                useCaseModule,
                resourceModule,

                activityModule,
                translationModule,
                selectLanguageModule,
            )
        }
    }
}
