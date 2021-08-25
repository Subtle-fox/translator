package com.andyanika.translator.repository.local.di.koin

import android.content.Context
import androidx.room.Room
import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.repository.local.DatabaseTranslator
import com.andyanika.translator.repository.local.LocalRepositoryImpl
import com.andyanika.translator.repository.local.ModelsAdapter
import com.andyanika.translator.repository.local.TranslatorDao
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.single

val localRepositoryModule = module {
    single { LocalRepositoryImpl(get(), get(), get(), get(named("io"))) } bind LocalRepository::class

    single {
        Room.databaseBuilder(get(), DatabaseTranslator::class.java, "words_database")
            .build()
            .translatorDao()
    } bind TranslatorDao::class

    single {
        val ctx: Context = get()
        ctx.getSharedPreferences("config", Context.MODE_PRIVATE)
    }

    single<ModelsAdapter>()
}
