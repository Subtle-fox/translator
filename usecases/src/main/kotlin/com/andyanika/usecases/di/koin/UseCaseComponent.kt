package com.andyanika.usecases.di.koin

import com.andyanika.translator.repository.local.di.koin.localRepositoryModule
import com.andyanika.translator.repository.remote.di.koin.networkModule
import org.koin.core.component.KoinComponent

object UseCaseComponent : KoinComponent {
    fun getModule() = useCaseModule + localRepositoryModule + networkModule
}
