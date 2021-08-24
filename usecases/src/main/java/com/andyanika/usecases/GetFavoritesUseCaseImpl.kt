package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase
import com.andyanika.translator.common.models.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

class GetFavoritesUseCaseImpl @Inject constructor(
    private val repository: LocalRepository,
    @param:Named("io") private val ioScheduler: Scheduler
) : GetFavoritesUseCase {
    override fun run(limit: Int): Flowable<List<FavoriteModel>> {
        return repository
            .favorites
            .subscribeOn(ioScheduler)
            .take(limit.toLong())
    }
}
