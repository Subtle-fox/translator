package com.andyanika.translator.di.koin

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val dispatchersModule = module {
    single(named("io")) { Dispatchers.IO }

    single(named("computation")) { Dispatchers.Default }

    single(named("ui")) { Dispatchers.Main }
}
