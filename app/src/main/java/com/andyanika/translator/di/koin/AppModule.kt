package com.andyanika.translator.di.koin

import com.andyanika.translator.common.interfaces.Resources
import org.koin.dsl.module

val appModule = module {
    single { context -> object : Resources {
        override fun getString(resourceId: Int): String {
            TODO("Not yet implemented")
        }

        override fun getString(resourceName: String?): String {
            TODO("Not yet implemented")
        }

    } }
}
