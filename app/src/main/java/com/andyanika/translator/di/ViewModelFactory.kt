package com.andyanika.translator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject internal constructor(private val mProviders: Map<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val provider = mProviders[modelClass]
        if (provider != null) {
            return provider.get() as T
        }
        throw IllegalArgumentException("No such provider for " + modelClass.canonicalName)
    }
}
