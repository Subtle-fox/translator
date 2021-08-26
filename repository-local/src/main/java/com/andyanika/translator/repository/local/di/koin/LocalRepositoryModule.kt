package com.andyanika.translator.repository.local.di.koin

import android.content.Context
import androidx.room.Room
import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.repository.local.DatabaseTranslator
import com.andyanika.translator.repository.local.LocalRepositoryImpl
import com.andyanika.translator.repository.local.ModelsAdapter
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.single

val localRepositoryModule = module {
    single<LocalRepository> { LocalRepositoryImpl(get(), get(), get(), get(named("io"))) }

    single {
        Room.databaseBuilder(get(), DatabaseTranslator::class.java, "words_database")
            .build()
            .translatorDao()
    }

    single {
        val ctx: Context = get()
        ctx.getSharedPreferences("config", Context.MODE_PRIVATE)
    }

    single<ModelsAdapter>()
}
