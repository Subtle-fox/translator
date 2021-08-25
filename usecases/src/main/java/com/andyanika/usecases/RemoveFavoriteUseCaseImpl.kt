package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

class RemoveFavoriteUseCaseImpl @Inject constructor(
    private val repository: LocalRepository,
    @Named("io") private val ioScheduler: Scheduler
) : RemoveFavoriteUseCase {
    override fun run(wordId: Int): Completable {
        return Completable
            .fromRunnable { repository.removeFavorite(wordId) }
            .subscribeOn(ioScheduler)
    }
}
