package com.andyanika.translator.di.koin

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val schedulersModule = module {
    single(named("io")) { Schedulers.io() }

    single(named("computation")) { Schedulers.computation() }

    single(named("ui")) { AndroidSchedulers.mainThread() }
}
