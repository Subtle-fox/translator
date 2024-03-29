package com.andyanika.resources.di.koin

import com.andyanika.resources.ResourceImpl
import core.interfaces.Resources
import org.koin.dsl.bind
import org.koin.dsl.module

val resourceModule = module {
    factory { ResourceImpl(get()) } bind Resources::class
}
