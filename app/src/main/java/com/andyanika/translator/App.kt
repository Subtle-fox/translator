package com.andyanika.translator

import android.util.Log
import com.andyanika.translator.di.DaggerAppComponent
import com.andyanika.translator.repository.remote.DaggerRemoteRepositoryComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .remoteRepositoryComponent(DaggerRemoteRepositoryComponent.create())
            .create(this)
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
}
